package mygdx.game.model.data;

import mygdx.game.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class RankData implements Serializable {
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
