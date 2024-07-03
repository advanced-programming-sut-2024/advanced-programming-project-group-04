package com.mygdx.game.controller;

import java.net.Socket;

public class GameServer extends Thread {
    private final Socket socket1;
    private final Socket socket2;

    public GameServer(Socket socket1, Socket socket2) {
        this.socket1 = socket1;
        this.socket2 = socket2;
    }

    @Override
    public void run() {

    }
}
