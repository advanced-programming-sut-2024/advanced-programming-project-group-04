package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.card.Card;

public class Decoy implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        // TODO : man ridam be decoy
        /* *
        Position callerCardPosition = gameManager.findCardInGameForCurrentPlayer(callerCard);
        gameManager.placeCard(new Card(AllCards.Decoy), callerCardPosition);
        gameManager.removeCard(callerCard);
        /* */
    }
}
