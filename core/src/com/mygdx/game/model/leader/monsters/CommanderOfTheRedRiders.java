package com.mygdx.game.model.leader.monsters;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class CommanderOfTheRedRiders extends Leader {
    public CommanderOfTheRedRiders() {
        super("Commander of the Red Riders");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
