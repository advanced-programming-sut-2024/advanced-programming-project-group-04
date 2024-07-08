package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.card.Card;

public class TightBond implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        int tightComrades = 0;
        for (Card sampleCard : gameManager.getRowFromCard(callerCard)) {
            if (callerCard.getName().equals(sampleCard.getName())) {
                tightComrades++;
            }
        }
        callerCard.setHowManyTightBond(tightComrades);
    }
}
