package mygdx.game.model.data;

import mygdx.game.model.GameHistory;
import mygdx.game.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameHistoryData implements Serializable {
    private final ArrayList<GameHistory> matchHistory;

    public GameHistoryData(Player player) {
        this.matchHistory = player.getMatchHistory();
    }

    public ArrayList<GameHistory> getMatchHistory() { return this.matchHistory; }
}
