package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;

import java.util.ArrayList;

public class Mardroeme implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        Position callerCardPosition = gameManager.findCardInGameForCurrentPlayer(callerCard);
        ArrayList<Card> row = gameManager.getCardRowFromPosition(callerCardPosition);
        for (Card card : row) {
            if (card.isBerserker()){
                card.getAbility().run(gameManager, card);
            }
        }
    }
}
