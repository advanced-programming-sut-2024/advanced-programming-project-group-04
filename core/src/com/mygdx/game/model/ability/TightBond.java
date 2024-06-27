package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;

public class TightBond implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        Position callerCardPosition = gameManager.findCardInGameForCurrentPlayer(callerCard);
        int tightComrades = 0;
        for (Card sampleCard : gameManager.getCardRowFromPosition(callerCardPosition)) {
            if (callerCard.getName().equals(sampleCard.getName())) {
                tightComrades ++;
            }
        }
        callerCard.setHowManyTightBond(tightComrades);
    }
}
