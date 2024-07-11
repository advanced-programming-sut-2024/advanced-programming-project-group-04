package mygdx.game.model.data;

import mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.Vector;

public class RankData {
    private ArrayList<PlayerRankData> allPlayers;

    public RankData(Vector<Player> players) {
        ArrayList<PlayerRankData> rankData = new ArrayList<>();
        for (Player player : players) {
            rankData.add(new PlayerRankData(player));
        }
        this.allPlayers = rankData;
    }

    public ArrayList<PlayerRankData> getAllPlayers() { return this.allPlayers; }
}
