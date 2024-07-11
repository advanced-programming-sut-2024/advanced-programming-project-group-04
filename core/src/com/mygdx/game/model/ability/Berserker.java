package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.Position;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;

public class Berserker implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
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
