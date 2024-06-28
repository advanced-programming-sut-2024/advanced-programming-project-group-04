package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;

public class Muster implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        for (Card sampleCard : currentPlayer.getHand()) {
            if (callerCard.musterEquality(sampleCard)) {
                currentPlayer.removeFromHand(sampleCard);
                gameManager.placeCard(sampleCard);
            }
        }

        for (Card sampleCard : currentPlayer.getDeckInGame()) {
            if (callerCard.musterEquality(sampleCard)) {
                currentPlayer.removeFromDeckInGame(sampleCard);
                gameManager.placeCard(sampleCard);
            }
        }
    }
}
