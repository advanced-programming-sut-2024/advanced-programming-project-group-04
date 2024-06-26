package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.card.Card;

public class Spy implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        gameManager.drawRandomCardFromDeck();
        gameManager.drawRandomCardFromDeck();
    }
}
