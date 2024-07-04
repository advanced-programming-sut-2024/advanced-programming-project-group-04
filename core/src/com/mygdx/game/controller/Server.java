package com.mygdx.game.controller;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.*;
import com.mygdx.game.model.leader.Leader;
import com.mygdx.game.model.leader.skellige.KingBran;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    private static final Vector<Player> allPlayers = new Vector<>();
    private static final ConcurrentHashMap<Player, String> passwords = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, Integer> gameRequests = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Player, Server> allSessions = new ConcurrentHashMap<>();

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

        Gson gson = new Gson();
        for (File file : subFiles) {
            FileHandle fileHandle = new FileHandle(file + "/login-data.json");
            String json = fileHandle.readString();
            Player player = gson.fromJson(json, Player.class);
            allPlayers.add(player);
            fileHandle = new FileHandle(file + "/password.json");
            json = fileHandle.readString();
            String password = gson.fromJson(json, String.class);
            passwords.put(player, password);
            System.out.println(password);
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

    @Override
    public void run() {
        try {
            while (true) {
                ServerCommand cmd = (ServerCommand) in.readObject();
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
                }

            }
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
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

    private void sendGameRequest() throws IOException, ClassNotFoundException {
        int senderId = (int) in.readObject();
        int receiverId = (int) in.readObject();

        if (gameRequests.containsKey(receiverId)) {
            if (gameRequests.get(receiverId).equals(senderId)) {
                gameRequests.remove(receiverId);
                gameRequests.remove(senderId);

                Player p1 = findPlayerById(senderId);
                Player p2 = findPlayerById(receiverId);
                if (p1 == null || p2 == null) throw new RuntimeException();

                GameServer gameServer = new GameServer(allSessions.get(p1).getSocket(), allSessions.get(p1).getSocket());
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
        out.writeObject(faction);
    }

    private void selectLeader() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String leaderName = (String) in.readObject();

        Faction faction = player.getSelectedFaction();
        Class<?> leaderClass = Class.forName("com.mygdx.game.model.leader." + faction.getAssetName().toLowerCase() + "." + leaderName);
        Leader leader = (Leader) leaderClass.getConstructor().newInstance();

        System.out.println(leader.getName());

        player.getDeck().setLeader(leader);
        out.writeObject(leader);
    }

    private void selectCard() throws IOException, ClassNotFoundException {
        AllCards allCard = (AllCards) in.readObject();
        Card card = new Card(allCard);
        player.getDeck().addCard(card);
        out.writeObject(card);
    }

    private void deSelectCard() throws IOException, ClassNotFoundException {
        AllCards allCard = (AllCards) in.readObject();
        Card card = player.getDeck().removeCardFromAllCard(allCard);
        if (card == null) throw new RuntimeException();
        out.writeObject(card);
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
