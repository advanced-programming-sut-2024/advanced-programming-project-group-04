package mygdx.game.controller;

import mygdx.game.controller.commands.ServerCommand;

public class PasswordRecoveryController {
    private Client client;
    private String username;

    public PasswordRecoveryController(Client client) {
        this.client = client;
    }

    public ControllerResponse confirmButtonClicked(String username) {
        boolean isFail = true;
        String errorMessage = "User not found";

        if (username.isEmpty()) errorMessage = "Enter username!";
        else {
            boolean result = client.sendToServer(ServerCommand.DOES_USERNAME_EXIST, username);
            if (result) {
                this.username = username;
                errorMessage = "";
                isFail = false;
            }
        }

        return new ControllerResponse(isFail, errorMessage);
    }

    public ControllerResponse submitAnswerButtonClicked(String answer) {
        boolean isFail = true;
        String errorMessage = "Incorrect Answer!";

        if (answer.isEmpty()) errorMessage = "Enter answer!";
        else {
            boolean result = client.sendToServer(ServerCommand.VALIDATE_ANSWER, answer, username);
            if (result) {
                isFail = false;
                errorMessage = "";
            }
        }

        return new ControllerResponse(isFail, errorMessage);
    }

    public String getForgetPasswordQuestion() {
        return client.sendToServer(ServerCommand.FETCH_QUESTION, username);
    }

    public void changePassword(String newPassword) {
        client.sendToServer(ServerCommand.RESET_PASSWORD, newPassword, username);
    }
}
