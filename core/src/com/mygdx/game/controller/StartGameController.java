package mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;

public class StartGameController {
    private Main game;

    public StartGameController(Main game) {
        this.game = game;
    }

    public ControllerResponse findPlayerButtonClicked(String username) {
        String error = "";
        boolean isFail = true;
        Client client = game.getClient();
        if (game.getLoggedInPlayer().getUsername().equals(username)) error = "You can't start a game with yourself";
        else if (client.sendToServer(ServerCommand.DOES_USERNAME_EXIST, username).equals(false)) error = "Player not found";
        else if (client.sendToServer(ServerCommand.IS_ONLINE, username).equals(false)) error = "Player is offline";
        else isFail = client.sendToServer(ServerCommand.START_GAME_REQUEST, username);

        return new ControllerResponse(isFail, error);
    }
}
