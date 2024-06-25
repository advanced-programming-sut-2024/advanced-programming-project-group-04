package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

public class Transformers implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        if (callerCard.getAllCard().equals(AllCards.Cow)) {
            Card card = new Card(AllCards.BovineDefenseForce);
            gameManager.placeCard(card);
        }
        if (callerCard.getAllCard().equals(AllCards.Kambi)) {
            Card card = new Card(AllCards.Hemdall);
            gameManager.placeCard(card);
        }
    }
}
