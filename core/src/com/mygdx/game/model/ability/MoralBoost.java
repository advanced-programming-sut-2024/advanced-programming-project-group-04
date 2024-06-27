package com.mygdx.game.model.ability;


import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;

public class MoralBoost implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        Position callerCardPosition = gameManager.findCardInGameForCurrentPlayer(callerCard);
        for (Card sampleCard : gameManager.getCardRowFromPosition(callerCardPosition)) {
            if (!callerCard.equals(sampleCard)) {
                sampleCard.setHowManyMoralBoostAffected(sampleCard.getHowManyMoralBoostAffected() + 1);
            }
        }
    }
}
