package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.Position;

public class Berserker implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        Position callerCardPosition = gameManager.findCardInGameForCurrentPlayer(callerCard);
        if (callerCard.getAllCard().equals(AllCards.YoungBerserker)) {
            Card transformedCard = new Card(AllCards.TransformedYoungVidkaarl);
            gameManager.placeCard(transformedCard, callerCardPosition);
            gameManager.removeCard(callerCard);
        }

        if (callerCard.getAllCard().equals(AllCards.Berserker)) {
            Card transformedCard = new Card(AllCards.TransformedVidkaarl);
            gameManager.placeCard(transformedCard, callerCardPosition);
            gameManager.removeCard(callerCard);
        }
    }
}
