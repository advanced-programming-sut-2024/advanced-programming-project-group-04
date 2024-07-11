package mygdx.game.controller;

import com.google.gson.Gson;
import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.data.PlayerProfileData;

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
        return gson.fromJson(profileDataJson, PlayerProfileData.class);
    }
}
