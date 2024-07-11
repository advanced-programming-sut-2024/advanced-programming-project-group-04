package mygdx.game.model.data;

import mygdx.game.model.Player;
import mygdx.game.model.Rank;

import java.io.Serializable;

public class PlayerRankData implements Serializable {
    private final String username;
    private final int LP;
    private final int winCount;
    private final int drawCount;
    private final int lossCount;
    private final Rank rank;

    public PlayerRankData(Player player) {
        this.username = player.getUsername();
        this.LP = player.getLP();
        this.winCount = player.getWinCount();
        this.drawCount = player.getDrawCount();
        this.lossCount = player.getLossCount();
        this.rank = player.getRank();
    }

    public int getLP() {
        return this.LP;
    }

    public int getWinCount() { return this.winCount; }

    public int getLossCount() { return this.lossCount; }

    public int getDrawCount() { return this.drawCount; }

    public String getUsername() { return this.username; }

    public Rank getRank() { return this.rank; }
}
