package com.mygdx.game.model.leader.scoiatael;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class QueenOfDolBlathanna extends Leader {
    public QueenOfDolBlathanna() {
        super("Queen of Dol Blathanna");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
    