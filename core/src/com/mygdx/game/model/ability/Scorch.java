package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

import java.util.ArrayList;

public class Scorch implements Ability {

    @Override
    public void run(GameManager gameManager , Card callerCard){
        if (callerCard.getAllCard().equals(AllCards.Toad)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Range);
            for (Card card : cards) {
                gameManager.removeCard(card, Position.Range); //TODO @Arvin CHERA REMOVE CARD POSITION MIGIRE? OON KE YEKTAST JAYE FELISH
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Schirru)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Siege);
            for (Card card : cards) {
                gameManager.removeCard(card, Position.Siege); //TODO @Arvin CHERA REMOVE CARD POSITION MIGIRE? OON KE YEKTAST JAYE FELISH
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Villentretenmerth)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Melee);
            for (Card card : cards) {
                gameManager.removeCard(card, Position.Melee); //TODO @Arvin CHERA REMOVE CARD POSITION MIGIRE? OON KE YEKTAST JAYE FELISH
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Scorch) || callerCard.getAllCard().equals(AllCards.ClanDimunPirate)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInField();
            for (Card card : cards) {
                Position cardPosition = gameManager.findCardInGameForCurrentPlayer(card);
                gameManager.removeCard(card, cardPosition); //TODO @Arvin CHERA REMOVE CARD POSITION MIGIRE? OON KE YEKTAST JAYE FELISH
            }
        }
    }
}
