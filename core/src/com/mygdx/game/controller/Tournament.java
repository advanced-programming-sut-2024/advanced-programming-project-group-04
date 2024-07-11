package mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.Vector;

public class Tournament extends Thread {
    private final ArrayList<Server> sessions;
    private final ArrayList<GameServer> runningGames;
    private final ArrayList<Server> winners;

    public Tournament(Vector<Player> players) {
        winners = new ArrayList<>();
        runningGames = new ArrayList<>();
        sessions = new ArrayList<>();
        for (Player player : players) {
            sessions.add(Server.allSessions.get(player));
        }
        makeMatchups();
    }

    public void makeMatchups() {
        runningGames.add(new GameServer(sessions.get(0), sessions.get(1)));
        runningGames.add(new GameServer(sessions.get(2), sessions.get(3)));
        runningGames.get(0).start();
        runningGames.get(1).start();
    }

    @Override
    public void run() {
        while (true) {
            boolean moveToNextStage = false;
            if (runningGames.get(0).isEnded()) {
                winners.add(runningGames.get(0).getWinner());
                moveToNextStage = true;
            }
            if (runningGames.get(1).isEnded()) {
                winners.add(runningGames.get(1).getWinner());
                if (moveToNextStage) {
                    runningGames.add(new GameServer(winners.get(0), winners.get(1)));
                    runningGames.get(2).start();
                    while (true) {
                        if (runningGames.get(2).isEnded()) {
                            return;
                        }
                    }
                }
            }
        }
    }
}
