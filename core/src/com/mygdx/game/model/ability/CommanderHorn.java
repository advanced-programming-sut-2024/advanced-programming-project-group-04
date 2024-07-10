package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

public class CommanderHorn implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        if (callerCard.getAllCard().equals(AllCards.CommandersHorn)) {
            for (Card sampleCard : gameManager.getRowFromCard(callerCard)) {
                sampleCard.setIsCommandersHornAffected(true);
            }
        } else {
            for (Card sampleCard : gameManager.getRowFromCard(callerCard)) {
                if (!sampleCard.equals(callerCard)) {
                    sampleCard.setIsCommandersHornAffected(true);
                }
            }
        }
    }
}
