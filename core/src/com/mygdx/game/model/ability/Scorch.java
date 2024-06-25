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
                gameManager.removeCard(card); 
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Schirru)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Siege);
            for (Card card : cards) {
                gameManager.removeCard(card); 
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Villentretenmerth)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInRow(Position.Melee);
            for (Card card : cards) {
                gameManager.removeCard(card); 
            }
        }

        if (callerCard.getAllCard().equals(AllCards.Scorch) || callerCard.getAllCard().equals(AllCards.ClanDimunPirate)) {
            ArrayList<Card> cards = gameManager.getMaximumPowerInField();
            for (Card card : cards) {
                gameManager.removeCard(card); 
            }
        }
    }
}
