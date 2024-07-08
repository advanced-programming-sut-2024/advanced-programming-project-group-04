package com.mygdx.game.controller;

import com.mygdx.game.controller.commands.ClientCommand;
import com.mygdx.game.controller.commands.GameClientCommand;
import com.mygdx.game.controller.commands.ServerCommand;
import com.mygdx.game.view.GameMenu;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean isRunning = true;
    private boolean outputReceived = false;
    private boolean clientCommandReceived = false;
    private boolean gameCommandReceived = false;
    private GameController gameController;
    private Object obj;
    private ArrayList<Object> inputs;

    public Client(String ip) {
        try {
            this.socket = new Socket(ip, 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void closeConnection() {
        sendToServer(ServerCommand.CLOSE_CONNECTION);
        isRunning = false;
        try {
            socket.close();
            in.close();
            out.close();
            this.interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T sendToServer(Object... inputs) {
        try {
            for (Object obj : inputs) {
                out.writeObject(obj);
            }

            while (!isOutputReceived());

            T response = (T) this.obj;
            this.obj = null;
            setOutputReceived(false);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToServerVoid(Object... inputs) {
        try {
            for (Object obj : inputs) {
                out.writeObject(obj);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized boolean isOutputReceived() {
        return this.outputReceived;
    }

    private synchronized void setOutputReceived(boolean outputReceived) {
        this.outputReceived = outputReceived;
    }

    public synchronized boolean isClientCommandReceived() { return this.clientCommandReceived; }

    public synchronized void setClientCommandReceived(boolean clientCommandReceived) { this.clientCommandReceived = clientCommandReceived; }

    public synchronized boolean isGameCommandReceived() { return this.gameCommandReceived; }

    public synchronized void setGameCommandReceived(boolean gameCommandReceived) { this.gameCommandReceived = gameCommandReceived; }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Object obj = in.readObject();
                if (obj != null) System.out.println("Client read object: " + obj);
                else System.out.println("Client read object: null");
                if (obj instanceof ClientCommand) {
                    this.obj = obj;
                    setClientCommandReceived(true);
                } else if (obj instanceof GameClientCommand) {
                    System.out.println("GameClientCommand 1");
                    inputs = new ArrayList<>();
                    inputs.add(obj);
                    System.out.println("GameClientCommand 2");
                    while (obj != GameClientCommand.EOF) {
                        System.out.println("GameClientCommand 3");
                        obj = in.readObject();
                        inputs.add(obj);
                    }
                    System.out.println("GameClientCommand 4");
                    setGameCommandReceived(true);
                    System.out.println("GameClientCommand 5");
                } else {
                    this.obj = obj;
                    setOutputReceived(true);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ClientCommand getClientCommand() { return (ClientCommand) this.obj; }

    public ArrayList<Object> getGameCommand() { return this.inputs; }
}
