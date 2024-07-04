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
            Client client = game.getClient();
            if (client.sendToServer(ServerCommand.DOES_USERNAME_EXIST, username).equals(false)) errorMessage = "No such player exists";
            else if (client.sendToServer(ServerCommand.HAS_ACTIVE_SESSION, username)) errorMessage = "Player logged in another device";
            else if (client.sendToServer(ServerCommand.VALIDATE_PASSWORD, username, password).equals(false)) errorMessage = "Wrong password";
            else {
                Player player = client.sendToServer(ServerCommand.FETCH_USER, username);
                client.sendToServer(ServerCommand.LOGIN_PLAYER, player.getId());
                game.setLoggedInPlayer(player);
                isFail = false;
            }
        }

        return new ControllerResponse(isFail, errorMessage);
    }
}
