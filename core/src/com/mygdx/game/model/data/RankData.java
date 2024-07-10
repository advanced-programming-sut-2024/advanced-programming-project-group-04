package mygdx.game.model.data;

import mygdx.game.model.Player;

import java.util.ArrayList;

public class RankData {
    private ArrayList<PlayerRankData> allPlayers;

    public RankData(ArrayList<Player> players) {
        ArrayList<PlayerRankData> rankData = new ArrayList<>();
        for (Player player : players) {
            rankData.add(new PlayerRankData(player));
        }
        this.allPlayers = rankData;
    }

    public ArrayList<PlayerRankData> getAllPlayers() { return this.allPlayers; }
}
