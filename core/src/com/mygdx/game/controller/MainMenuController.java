package com.mygdx.game.controller;

import com.mygdx.game.Main;
import com.mygdx.game.model.Player;

public class MainMenuController {
    private final Main game;

    public MainMenuController(Main game) {
        this.game = game;
    }

    public void logout() {
        game.getClient().sendToServer(ServerCommand.LOGOUT_PLAYER, game.getLoggedInPlayer().getId());
        game.setLoggedInPlayer(null);
    }

    public ControllerResponse startNewGame() {
        String errorMessage = "";
        boolean isFail = true;

        Player player = game.getLoggedInPlayer();
        if (player.canStartGame()) {
            errorMessage = "Please complete your deck first";
        } else {
            isFail = false;
        }

        return new ControllerResponse(isFail, errorMessage);
    }
}
