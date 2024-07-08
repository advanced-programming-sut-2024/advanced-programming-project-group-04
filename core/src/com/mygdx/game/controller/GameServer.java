package mygdx.game.controller;

public class GameServer extends Thread {
    Server mySession;
    Server enemySession;

    public GameServer(Server mySession, Server enemySession) {
        this.mySession = mySession;
        this.enemySession = enemySession;
        this.start();
    }

    @Override
    public void run() {
        mySession.sendToClient(ClientCommand.START_GAME);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        enemySession.sendToClient(ClientCommand.START_GAME);
    }
}
