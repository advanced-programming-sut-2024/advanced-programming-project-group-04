package mygdx.game.model;

import java.io.Serializable;

public class GameHistory implements Serializable {
    private final GameResult result;
    private final int id;
    private final String opponentUsername;
    private final int opponentId;

    public GameHistory(GameResult result, int id, String opponentUsername, int opponentId) {
        this.result = result;
        this.id = id;
        this.opponentUsername = opponentUsername;
        this.opponentId = opponentId;
    }

    public GameResult getResult() { return this.result; }

    public int getId() { return this.id; }

    public String getOpponentUsername() { return this.opponentUsername; }

    public int getOpponentId() { return this.opponentId; }
}
