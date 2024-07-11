package mygdx.game.controller;

import com.google.gson.Gson;
import mygdx.game.Main;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.GameHistory;
import mygdx.game.model.data.GameHistoryData;
import mygdx.game.model.data.PlayerRankData;
import mygdx.game.model.data.RankData;

import java.util.ArrayList;

public class ProfileController {
    private Client client;
    private Main game;

    public ProfileController(Main game) {
        this.game = game;
        this.client = game.getClient();
    }

    public ArrayList<PlayerRankData> getAllPlayersRankData() {
        String dataJson = client.sendToServer(ServerCommand.GET_RANK_DATA);
        Gson gson = new Gson();
        RankData rankData = gson.fromJson(dataJson, RankData.class);
        return rankData.getAllPlayers();
    }

    public void toggleTwoFA() {
        client.sendToServer(ServerCommand.TOGGLE_2FA);
    }

    public void changeCredentials(String fieldType, String input) {
        if (fieldType.equals("Username")) {
            client.sendToServer(ServerCommand.CHANGE_USERNAME, input);
        } else if (fieldType.equals("Password")) {
            client.sendToServer(ServerCommand.CHANGE_PASSWORD, input);
        } else if (fieldType.equals("Email")) {
            client.sendToServer(ServerCommand.CHANGE_EMAIL, input);
        }
    }

    public void setQuestion(String question, String answer) {
        client.sendToServer(ServerCommand.SET_QUESTION, question, answer);
    }

    public ArrayList<GameHistory> getMatchHistory() {
        Gson gson = CustomGson.getGson();
        String gameHistoryDataJson = game.getClient().sendToServer(ServerCommand.GET_GAME_HISTORY, game.getLoggedInPlayer().getId());
        GameHistoryData gameHistoryData = gson.fromJson(gameHistoryDataJson, GameHistoryData.class);
        return gameHistoryData.getMatchHistory();
    }

}
