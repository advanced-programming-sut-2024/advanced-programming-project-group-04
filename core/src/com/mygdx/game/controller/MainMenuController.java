package mygdx.game.controller;

import com.google.gson.Gson;
import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.Player;
import mygdx.game.model.data.ChatData;
import mygdx.game.model.data.FriendData;
import mygdx.game.model.data.MessageData;
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
        Gson gson = CustomGson.getGson();
        String friendDataJson = game.getClient().sendToServer(ServerCommand.GET_FRIENDS);
        FriendData friendData = gson.fromJson(friendDataJson, FriendData.class);
        return friendData.getFriends();
    }

    public ArrayList<PlayerFriendData> getIncomingFriendRequests() {
        Gson gson = CustomGson.getGson();
        String friendDataJson = game.getClient().sendToServer(ServerCommand.GET_INCOMING_FRIEND_REQUESTS);
        FriendData friendData = gson.fromJson(friendDataJson, FriendData.class);
        return friendData.getFriends();
    }

    public boolean isFriendOnline(String username) {
        return game.getClient().sendToServer(ServerCommand.IS_ONLINE, username);
    }

    public void acceptFriendRequest(int id) {
        game.getClient().sendToServer(ServerCommand.ACCEPT_FRIEND_REQUEST, id);
    }

    public void rejectFriendRequest(int id) {
        game.getClient().sendToServer(ServerCommand.REJECT_FRIEND_REQUEST, id);
    }

    public ArrayList<MessageData> getChatWithFriend(int id) {
        Gson gson = CustomGson.getGson();
        String chatDataJson = game.getClient().sendToServer(ServerCommand.GET_MESSAGES, id);
        ChatData chatData = gson.fromJson(chatDataJson, ChatData.class);
        return chatData.getMessageList();
    }

    public void sendMessage(String messageText, int friendId) {
        game.getClient().sendToServer(ServerCommand.SEND_MESSAGE, messageText, friendId);
    }

    public ControllerResponse sendFriendRequest(String friendUsername) {
        String errorMessage = "";
        boolean isFailed = true;

        if (friendUsername.isEmpty()) errorMessage = "Enter friend username";
        else if (game.getClient().sendToServer(ServerCommand.DOES_USERNAME_EXIST, friendUsername)) errorMessage = "Player not found!";
        else {
            isFailed = false;
            game.getClient().sendToServer(ServerCommand.SEND_FRIEND_REQUEST, friendUsername);
        }

        return new ControllerResponse(isFailed, errorMessage);
    }

    public void challengeFriend(String username) {
        game.getClient().sendToServer(ServerCommand.START_GAME_REQUEST, username);
    }
}
