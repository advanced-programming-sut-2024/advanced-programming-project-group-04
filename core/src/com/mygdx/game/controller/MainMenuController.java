package com.mygdx.game.controller;

import com.mygdx.game.Main;

public class MainMenuController {
    private final Main game;

    public MainMenuController(Main game) {
        this.game = game;
    }

    public void logout() {
        game.setLoggedInPlayer(null);
    }

    public ControllerResponse startNewGame() {
        String errorMessage = "You're not supposed to press this yet";
        boolean isFail = true;

        // TODO: @Arman

        return new ControllerResponse(isFail, errorMessage);
    }
}
