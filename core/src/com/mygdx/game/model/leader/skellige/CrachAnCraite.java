package com.mygdx.game.model.leader.skellige;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class CrachAnCraite extends Leader {
    public CrachAnCraite() {
        super("Crach an Craite");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
