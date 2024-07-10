package com.mygdx.game.model.leader.skellige;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class CrachAnCraite extends Leader {
    public CrachAnCraite() {
        super("Crach an Craite");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherPlayer = gameManager.getOtherPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        for (int i = currentPlayer.getGraveyard().size() - 1; i >= 0; i--){
            currentPlayer.addToDeckInGame(currentPlayer.getGraveyard().get(i));
            currentPlayer.getGraveyard().remove(i);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Skellige/" + getAssetName() + ".jpg";
    }

}
