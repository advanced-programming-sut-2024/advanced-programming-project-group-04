package mygdx.game.controller;

import mygdx.game.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Client extends Thread {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean isRunning = true;
    private boolean isWaiting = false;
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
            setOutputReceived(false);
            setIsWaiting(true);
            out.writeObject(GeneralCommand.CLEAR);

            for (Object obj : inputs) {
                out.writeObject(obj);
            }

            while (isWaiting());

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
                    inputs = new ArrayList<>();
                    inputs.add(obj);
                    while (obj != GameClientCommand.EOF) {
                        obj = in.readObject();
                        inputs.add(obj);
                    }
                    setGameCommandReceived(true);
                } else if (obj instanceof GeneralCommand) {
                    this.obj = null;
                    setIsWaiting(false);
                }else {
                    this.obj = obj;
                    setOutputReceived(true);
                    setIsWaiting(false);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
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

    public synchronized boolean isWaiting() { return this.isWaiting; }

    public synchronized void setIsWaiting(boolean isWaiting) { this.isWaiting = isWaiting; }

    public ClientCommand getClientCommand() { return (ClientCommand) this.obj; }

    public ArrayList<Object> getGameCommand() { return this.inputs; }
}
