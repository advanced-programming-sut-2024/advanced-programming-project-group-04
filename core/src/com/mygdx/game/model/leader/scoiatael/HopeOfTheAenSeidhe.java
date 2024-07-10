package com.mygdx.game.model.leader.scoiatael;

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
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }
        // TODO : inam ajibe
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Scoiatael/" + getAssetName() + ".jpg";
    }

}
