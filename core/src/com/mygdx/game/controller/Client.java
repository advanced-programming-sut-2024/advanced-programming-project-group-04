package mygdx.game.controller;

import mygdx.game.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
    private final Main game;

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean isRunning = true;
    private boolean isReceived = false;
    private Object obj;

    public Client(Main game, String ip) {
        this.game = game;

        try {
            System.out.println("Started");
            this.socket = new Socket(ip, 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            while (!isReceived()) ;


            T response = (T) this.obj;
            this.obj = null;
            setReceived(false);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized boolean isReceived() {
        return this.isReceived;
    }

    private synchronized void setReceived(boolean received) {
        this.isReceived = received;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                this.obj = in.readObject();
                if (!(obj instanceof ClientCommand)) setReceived(true);

                else {
                    switch ((ClientCommand) obj) {
                        case START_GAME:
                            startGame();
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startGame() {
        game.startGame();
    }
}
