package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

public class CommanderHorn implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        Position positionOfCallerCard = gameManager.findCardInGameForCurrentPlayer(callerCard);

        if (callerCard.getAllCard().equals(AllCards.CommandersHorn)) {
            for (Card sampleCard : gameManager.getCardRowFromPosition(positionOfCallerCard)) {
                sampleCard.setIsCommandersHornAffected(true);
            }
        } else {
            for (Card sampleCard : gameManager.getCardRowFromPosition(positionOfCallerCard)) {
                if (!sampleCard.equals(callerCard)) {
                    sampleCard.setIsCommandersHornAffected(true);
                }
            }
        }
    }
}
