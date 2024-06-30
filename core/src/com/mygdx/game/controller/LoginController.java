package com.mygdx.game.controller;

import com.mygdx.game.Main;
import com.mygdx.game.model.Player;

public class LoginController {
    private final Main game;

    public LoginController(Main game) {
        this.game = game;
    }

    public ControllerResponse signInButtonClicked(String username, String password) {
        boolean isFail = true;
        String errorMessage = "";

        if (username.isEmpty()) errorMessage = "Enter your username";
        else if (password.isEmpty()) errorMessage = "Enter your password";
        else {
            Player player = Player.findPlayerByUsername(username);
            if (player == null) errorMessage = "No such player exists";
            else if (!player.validatePassword(password)) errorMessage = "Wrong password";
            else {
                game.setLoggedInPlayer(player);
                isFail = false;
            }
        }

        return new ControllerResponse(isFail, errorMessage);
    }
}
