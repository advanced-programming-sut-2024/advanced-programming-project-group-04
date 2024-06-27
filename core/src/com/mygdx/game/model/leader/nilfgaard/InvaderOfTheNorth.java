package com.mygdx.game.model.leader.nilfgaard;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class InvaderOfTheNorth extends Leader {
    public InvaderOfTheNorth() {
        super("Invader of the North");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherplayer = gameManager.getOtherPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }
        
        currentPlayer.addRandomCardToHandFromGrave();
        otherplayer.addRandomCardToHandFromGrave();
        
        currentPlayer.setIsLeaderUsed(true);
    }
}
