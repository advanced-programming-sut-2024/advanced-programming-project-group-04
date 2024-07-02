package com.mygdx.game.controller;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.mygdx.game.model.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {
    private static final Vector<Player> allPlayers = new Vector<>();

    static {
        Server.loadAllPlayers();
    }

    public static void loadAllPlayers() {
        File dataDir = new File("Data/Users");
        File[] subFiles = dataDir.listFiles();

        Gson gson = new Gson();
        for (File file : subFiles) {
            FileHandle fileHandle = new FileHandle(file + "/login-data.json");
            String json = fileHandle.readString();
            Player player = gson.fromJson(json, Player.class);
            allPlayers.add(player);
        }
    }

    public static Player findPlayerByUsername (String username) {
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public void createNewPlayer(String username, String password, String email, String nickname) {
        Player player = new Player(username, password, email, nickname);

        allPlayers.add(player);
        savePlayer(player);
    }

    public void savePlayer(Player player) {
        File file = new File("Data/Users/" + player.getId() + "/login-data.json");

        Gson gson = new Gson();
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            gson.toJson(player, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server() {

    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket socket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            String input;
            while (true) {
                ServerCommand cmd = (ServerCommand) in.readObject();
                System.out.println(cmd.name());
                if (cmd == ServerCommand.CHECK_USERNAME) {
                    String mmd = (String) in.readObject();
                    System.out.println(mmd);
                    if (Server.findPlayerByUsername(mmd) != null) {
                        out.writeObject(Boolean.TRUE);
                    } else out.writeObject(Boolean.FALSE);

                    in.close();
                    socket.close();
                    serverSocket.close();
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
