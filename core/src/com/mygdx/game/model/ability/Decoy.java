package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

public class Decoy implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        Position callerCardPosition = gameManager.findCardInGameForCurrentPlayer(callerCard);
        gameManager.placeCard(new Card(AllCards.Decoy), callerCardPosition);
        gameManager.removeCard(callerCard, callerCardPosition);
    }
}
