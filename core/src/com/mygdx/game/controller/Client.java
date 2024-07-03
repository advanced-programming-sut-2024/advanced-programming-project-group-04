package com.mygdx.game.controller;

import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Client() {
        try {
            this.socket = new Socket("127.0.0.1", 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T sendToServer(Object... inputs) {
        T response;
        try {
            for (Object obj : inputs) {
                out.writeObject(obj);
            }

            response = (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
