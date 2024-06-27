package com.mygdx.game.model.leader.Scoiataell;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.leader.Leader;

public class HopeOfTheAenSeidhe extends Leader{
    public HopeOfTheAenSeidhe() {
        super("Hope of the Aen Seidhe");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.setIsLeaderUsed(true);
    }
}
