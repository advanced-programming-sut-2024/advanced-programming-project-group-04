package com.mygdx.game.controller;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mygdx.game.controller.commands.GameServerCommand;
import com.mygdx.game.controller.commands.ServerCommand;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.*;
import com.mygdx.game.model.leader.Leader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

public class Server extends Thread {
    private static final Vector<Player> allPlayers = new Vector<>();
    private static final ConcurrentHashMap<Player, String> passwords = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, Integer> gameRequests = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Player, Server> allSessions = new ConcurrentHashMap<>();

    private boolean gameCommandReceived;
    private boolean outputReceived;
    private Object obj;
    private ArrayList<Object> inputs;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;

    static {
        Server.loadAllPlayers();
    }

    private static void loadAllPlayers() {
        File dataDir = new File("Data/Users");
        File[] subFiles = dataDir.listFiles();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Leader.class, new LeaderTypeAdapter());
        builder.registerTypeAdapter(Faction.class, new FactionTypeAdapter());
        Gson gson = builder.create();
        for (File file : subFiles) {
            FileHandle fileHandle = new FileHandle(file + "/login-data.json");
            String json = fileHandle.readString();
            Player player = gson.fromJson(json, Player.class);
            allPlayers.add(player);
            fileHandle = new FileHandle(file + "/password.json");
            json = fileHandle.readString();
            String password = gson.fromJson(json, String.class);
            passwords.put(player, password);
        }
    }

    private static Player findPlayerByUsername(String username) {
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    private static Player findPlayerById(int id) {
        for (Player player : allPlayers) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public <T> T sendToClient(Object... inputs) {
        T response;
        try {
            for (Object obj : inputs) {
                out.writeObject(obj);
            }

            while (!isOutputReceived());

            response = (T) this.obj;
            setOutputReceived(false);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToClientVoid(Object... inputs) {
        try {
            for (Object obj : inputs) {
                out.writeObject(obj);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean isGameCommandReceived() {
        return this.gameCommandReceived;
    }

    public synchronized void setGameCommandReceived(boolean gameCommandReceived) {
        this.gameCommandReceived = gameCommandReceived;
    }

    public ArrayList<Object> getInputs() {
        return this.inputs;
    }

    private synchronized boolean isOutputReceived() { return this.outputReceived; }

    private synchronized void setOutputReceived(boolean outputReceived) { this.outputReceived = outputReceived; }

    @Override
    public void run() {
        try {
            while (true) {
                Object input = in.readObject();

                if (input instanceof GameServerCommand) {
                    inputs = new ArrayList<>();
                    inputs.add(input);
                    while (input != GameServerCommand.EOF) {
                        input = in.readObject();
                        System.out.println("Server received inputs: " + input.toString());
                        inputs.add(input);
                    }
                    setGameCommandReceived(true);
                } else if (input instanceof ServerCommand) {
                    processCommand(input);
                } else {
                    this.obj = input;
                    setOutputReceived(true);
                }

            }
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void processCommand(Object input) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ServerCommand cmd = (ServerCommand) input;
        System.out.println(cmd.name());

        switch (cmd) {
            case DOES_USERNAME_EXIST:
                checkUsername();
                break;
            case REGISTER_USER:
                registerUser();
                break;
            case FETCH_USER:
                fetchUser();
                break;
            case FIND_USER_ACCOUNT:
                findUserAccount();
                break;
            case VALIDATE_PASSWORD:
                validatePassword();
                break;
            case HAS_ACTIVE_SESSION:
                hasActiveSession();
                break;
            case LOGIN_PLAYER:
                loginPlayer();
                break;
            case LOGOUT_PLAYER:
                logoutPlayer();
                break;

            case SELECT_FACTION:
                selectFaction();
                break;
            case SELECT_LEADER:
                selectLeader();
                break;
            case SELECT_CARD:
                selectCard();
                break;
            case DE_SELECT_CARD:
                deSelectCard();
                break;

            case IS_ONLINE:
                isOnline();
                break;
            case START_GAME_REQUEST:
                sendGameRequest();
                break;

            case CLOSE_CONNECTION:
                closeConnection();
                return;
        }
    }

    private void isOnline() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Player player = findPlayerByUsername(username);
        out.writeObject(allSessions.containsKey(player));
    }

    private void sendGameRequest() throws IOException, ClassNotFoundException {
        String receiverUsername = (String) in.readObject();
        Player receiver = findPlayerByUsername(receiverUsername);
        Player sender = this.player;
        int senderId = sender.getId();
        int receiverId = receiver.getId();

        if (gameRequests.containsKey(receiverId)) {
            if (gameRequests.get(receiverId).equals(senderId)) {
                gameRequests.remove(receiverId);
                gameRequests.remove(senderId);

                GameServer gameServer = new GameServer(this, allSessions.get(receiver));
                gameServer.start();
                out.writeObject(true);
            }
            else {
                out.writeObject(false);
            }
        } else {
            gameRequests.putIfAbsent(senderId, receiverId);
            gameRequests.replace(senderId, receiverId);
            out.writeObject(false);
        }
    }

    private void checkUsername() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        out.writeObject(Server.findPlayerByUsername(username) != null);
    }

    private void registerUser() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();
        String email = (String) in.readObject();
        String nickname = (String) in.readObject();

        Player player = new Player(username, email, nickname);

        allPlayers.add(player);
        passwords.put(player, password);
        savePlayerData(player, password);
        out.writeObject(null);
    }

    private void fetchUser() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Player player = Server.findPlayerByUsername(username);
        out.writeObject(player);
    }

    private void findUserAccount() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Player targetPlayer = Server.findPlayerByUsername(username);
        if (targetPlayer == null) out.writeObject(null);
        else {
            out.writeObject(targetPlayer.getUsername());
        }
    }

    private void validatePassword() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();
        Player player = findPlayerByUsername(username);
        if (player != null && passwords.get(player) != null && passwords.get(player).equals(password)) out.writeObject(true);
        else out.writeObject(false);
    }

    private void hasActiveSession() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Player player = findPlayerByUsername(username);
        if (allSessions.containsKey(player)) out.writeObject(true);
        else out.writeObject(false);
    }

    private void loginPlayer() throws IOException, ClassNotFoundException {
        int id = (int) in.readObject();
        Player player = findPlayerById(id);
        if (player == null || allSessions.containsKey(player)) throw new RuntimeException();
        else {
            allSessions.put(player, this);
            this.player = player;
            out.writeObject(null);
        }
    }

    private void logoutPlayer() throws IOException, ClassNotFoundException {
        int id = (int) in.readObject();
        Player player = findPlayerById(id);
        if (player == null || !allSessions.containsKey(player)) throw new RuntimeException();
        else {
            allSessions.remove(player);
            this.player = null;
            out.writeObject(null);
        }
    }

    private void selectFaction() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String factionName = (String) in.readObject();

        Class<?> factionClass = Class.forName("com.mygdx.game.model.faction." + factionName);
        Object faction = factionClass.getConstructor().newInstance();

        player.setFaction((Faction)faction);
        player.createNewDeck();
        updatePlayerData(player);
        out.writeObject(faction);
    }

    private void selectLeader() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String leaderName = (String) in.readObject();

        Faction faction = player.getSelectedFaction();
        Class<?> leaderClass = Class.forName("com.mygdx.game.model.leader." + faction.getAssetName().toLowerCase() + "." + leaderName);
        Leader leader = (Leader) leaderClass.getConstructor().newInstance();

        System.out.println(leader.getName());

        player.getDeck().setLeader(leader);
        updatePlayerData(player);
        out.writeObject(leader);
    }

    private void selectCard() throws IOException, ClassNotFoundException {
        AllCards allCard = (AllCards) in.readObject();
        Card card = new Card(allCard);
        player.getDeck().addCard(card);
        updatePlayerData(player);
        out.writeObject(card);
    }

    private void deSelectCard() throws IOException, ClassNotFoundException {
        AllCards allCard = (AllCards) in.readObject();
        Card card = player.getDeck().removeCardFromAllCard(allCard);
        if (card == null) throw new RuntimeException();
        updatePlayerData(player);
        out.writeObject(card);
    }

    private void closeConnection() throws IOException {
        out.writeObject(null);
        in.close();
        out.close();
        if (player != null) allSessions.remove(player);
    }

    private void savePlayerData(Player player, String password) {
        File dataFile = new File("Data/Users/" + player.getId() + "/login-data.json");
        File passwordFile = new File("Data/Users/" + player.getId() + "/password.json");

        Gson gson = new Gson();
        try {
            dataFile.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(dataFile);
            gson.toJson(player, writer);
            writer.close();
            writer = new FileWriter(passwordFile);
            gson.toJson(password, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePlayerData(Player player) {
        File file = new File("Data/Users/" + player.getId() + "/login-data.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Faction.class, new FactionTypeAdapter());
        builder.registerTypeAdapter(Leader.class, new LeaderTypeAdapter());
        Gson gson = builder.create();
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(player, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class LeaderTypeAdapter extends TypeAdapter<Leader> {

        @Override
        public void write(JsonWriter jsonWriter, Leader leader) throws IOException {
            if (leader == null) {
                jsonWriter.beginObject();
                jsonWriter.name("className");
                jsonWriter.value("");
                jsonWriter.endObject();
                return;
            }
            jsonWriter.beginObject();
            jsonWriter.name("className");
            jsonWriter.value(leader.getClass().getCanonicalName());
            jsonWriter.endObject();
        }

        @Override
        public Leader read(JsonReader jsonReader) throws IOException {
            jsonReader.beginObject();
            jsonReader.nextName();
            String leaderClassName = jsonReader.nextString();
            if (leaderClassName.isEmpty()) return null;
            else {
                try {
                    Class<?> clazz = Class.forName(leaderClassName);
                    Object obj = clazz.getConstructor().newInstance();
                    jsonReader.endObject();
                    return (Leader) obj;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class FactionTypeAdapter extends TypeAdapter<Faction> {
        @Override
        public void write(JsonWriter writer, Faction faction) throws IOException {
            if (faction == null) return;
            writer.beginObject();
            writer.name("clasName");
            writer.value(faction.getClass().getCanonicalName());
            writer.endObject();
        }

        @Override
        public Faction read(JsonReader reader) throws IOException {
            try {
                reader.beginObject();
                reader.nextName();
                Class<?> clazz = Class.forName(reader.nextString());
                reader.endObject();
                return (Faction) clazz.getConstructor().newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Server(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() { return this.socket; }

    public Player getPlayer() { return this.player; }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5000);
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                Server server = new Server(socket);
                server.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
