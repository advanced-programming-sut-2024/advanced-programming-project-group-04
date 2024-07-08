package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.Position;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;

public class CommanderHorn implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
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
