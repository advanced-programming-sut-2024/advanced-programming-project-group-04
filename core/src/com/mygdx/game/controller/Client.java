package com.mygdx.game.controller;

import com.mygdx.game.model.Player;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public Client() {
        try {
            this.socket = new Socket("127.0.0.1", 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUsernameTaken(String username) {
        Boolean response;
        try {
            out.writeObject(ServerCommand.CHECK_USERNAME);
            out.writeObject(username);
            response = (Boolean) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public void createNewPlayer(String username, String password, String email, String nickname) {

    }

    public Player findPlayerByUsername(String username) {
        return null;
    }

    public static void main(String[] args) {
        Client client = new Client();
        System.out.println(client.isUsernameTaken("mamad"));
    }
}
