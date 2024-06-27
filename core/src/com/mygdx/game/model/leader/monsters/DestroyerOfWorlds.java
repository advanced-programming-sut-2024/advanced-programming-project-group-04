package com.mygdx.game.model.leader.monsters;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class DestroyerOfWorlds extends Leader {
    public DestroyerOfWorlds() {
        super("Destroyer of Worlds");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
