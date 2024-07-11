package mygdx.game.controller;

import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.Player;
import mygdx.game.model.data.PlayerFriendData;
import mygdx.game.model.message.Message;

import java.util.ArrayList;

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
        if (!player.canStartGame()) {
            errorMessage = "Please complete your deck first";
        } else {
            isFail = false;
        }

        return new ControllerResponse(isFail, errorMessage);
    }

    public ArrayList<PlayerFriendData> getFriends() {
        // player.getFriends();
    }

    public ArrayList<PlayerFriendData> getIncomingFriendRequests() {
        // player.getIncomingFriendRequests();
    }

    public boolean isFriendOnline(String username) {
        return game.getClient().sendToServer(ServerCommand.IS_ONLINE, username);
    }

    public void acceptFriendRequest(int id) {

    }

    public void rejectFriendRequest(int id) {

    }

    public ArrayList<Message> getChatWithFriend(int id) {

    }

    public void sendMessage(String messageText, int friendId) {

    }

    public ControllerResponse sendFriendRequest(String friendUsername) {

    }
}
