package com.mygdx.game.model.leader.scoiatael;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class DaisyOfTheValley extends Leader {
    public DaisyOfTheValley() {
        super("Daisy of the Valley");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
