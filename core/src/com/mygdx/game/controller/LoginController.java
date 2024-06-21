package com.mygdx.game.controller;

import com.mygdx.game.model.Player;

public class LoginController {
    public static ControllerResponse signInButtonClicked(String username, String password) {
        boolean isFail = true;
        String errorMessage = "";

        if (username.isEmpty()) errorMessage = "Enter your username";
        else if (password.isEmpty()) errorMessage = "Enter your password";
        else {
            Player player = Player.findPlayerByUsername(username);
            if (player == null) errorMessage = "No such player exists";
            else if (!player.validatePassword(password)) errorMessage = "Wrong password";
            else {
                MainMenuController.setCurrentPlayer(player);
                isFail = false;
            }
        }

        return new ControllerResponse(isFail, errorMessage);
    }
}
