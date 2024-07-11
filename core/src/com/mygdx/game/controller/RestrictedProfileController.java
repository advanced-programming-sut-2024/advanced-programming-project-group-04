package mygdx.game.controller;

import com.google.gson.Gson;
import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.GameHistory;
import mygdx.game.model.data.GameHistoryData;
import mygdx.game.model.data.PlayerProfileData;

import java.util.ArrayList;

public class RestrictedProfileController {
    private Main game;
    private int playerId;

    public RestrictedProfileController(Main game, int playerId) {
        this.game = game;
        this.playerId = playerId;
    }

    public PlayerProfileData getPlayer() {
        Gson gson = CustomGson.getGson();
        String profileDataJson = game.getClient().sendToServer(ServerCommand.GET_PROFILE_DATA, playerId);
        PlayerProfileData playerData = gson.fromJson(profileDataJson, PlayerProfileData.class);
        return playerData;
    }

    public ArrayList<GameHistory> getMatchHistory() {
        Gson gson = CustomGson.getGson();
        String gameHistoryDataJson = game.getClient().sendToServer(ServerCommand.GET_GAME_HISTORY, playerId);
        GameHistoryData gameHistoryData = gson.fromJson(gameHistoryDataJson, GameHistoryData.class);
        return gameHistoryData.getMatchHistory();
    }
}
