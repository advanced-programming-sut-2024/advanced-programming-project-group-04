package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;

public class Muster implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        for (int i = currentPlayer.getHand().size() - 1; i >= 0; i--) {
            Card sampleCard = currentPlayer.getHand().get(i);
            if (callerCard.musterEquality(sampleCard)) {
                currentPlayer.removeFromHand(sampleCard);
                gameManager.placeCard(sampleCard);
            }
        }

        for (int i = currentPlayer.getDeckInGame().size() - 1; i >= 0; i--) {
            Card sampleCard = currentPlayer.getDeckInGame().get(i);
            if (callerCard.musterEquality(sampleCard)) {
                currentPlayer.removeFromDeckInGame(sampleCard);
                gameManager.placeCard(sampleCard);
            }
        }
    }
}
