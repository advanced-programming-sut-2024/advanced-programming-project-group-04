package com.mygdx.game.model.leader.Scoiataell;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class TheBeautiful extends Leader {
    public TheBeautiful() {
        super("The Beautiful");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
