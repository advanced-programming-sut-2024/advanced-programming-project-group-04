package mygdx.game.model;

import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;

public class Tournament {
    private Main player1Game;
    private Main player2Game;
    private Main player3Game;
    private Main player4Game;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player finalist1;
    private Player finalist2;


    public Tournament(Main player1, Main player2, Main player3, Main player4) {
        this.player1Game = player1;
        this.player2Game = player2;
        this.player3Game = player3;
        this.player4Game = player4;
        this.player1 = player1.getLoggedInPlayer();
        this.player2 = player2.getLoggedInPlayer();
        this.player3 = player3.getLoggedInPlayer();
        this.player4 = player4.getLoggedInPlayer();
        makeMatchups();
    }

    public void makeMatchups() {
        player1Game.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, player4.getUsername());
        player4Game.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, player1.getUsername());
        player3Game.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, player2.getUsername());
        player2Game.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, player3.getUsername());

        // SOMEHOW NOTE THE GAME RESULTS
    }

    public void makeFinalMatchup(Main Winner1, Main Winner2) {
        finalist1 = Winner1.getLoggedInPlayer();
        finalist2 = Winner2.getLoggedInPlayer();
        Winner1.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, finalist2.getUsername());
        player4Game.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, finalist1.getUsername());
    }


}
