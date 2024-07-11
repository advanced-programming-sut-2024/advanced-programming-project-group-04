package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.Position;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;

import java.util.ArrayList;

public class Scorch implements Ability {

    @Override
    public void run(GameManager gameManager, Card callerCard) {
        if (callerCard.getAllCard().equals(AllCards.Toad)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Range);
            if (cards != null) {
                for (Card card : cards) {
                    gameManager.removeCard(card);
                }
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Schirru)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Siege);
            if (cards != null) {
                for (Card card : cards) {
                    gameManager.removeCard(card);
                }
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Villentretenmerth)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Melee);
            if (cards != null) {
                for (Card card : cards) {
                    gameManager.removeCard(card);
                }
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Scorch) || callerCard.getAllCard().equals(AllCards.ClanDimunPirate)) {
            System.out.println("arvin taheri");
            ArrayList<Card> cards = gameManager.getMaximumPowerInField();
            if (cards != null) {
                for (Card card : cards) {
                    gameManager.removeCard(card);
                }
            }
            if (callerCard.getAllCard().equals(AllCards.Scorch)) {
                gameManager.removeCard(callerCard);
            }
        }
    }
}
