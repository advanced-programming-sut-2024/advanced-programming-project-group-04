package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;

import java.util.ArrayList;

public class Mardroeme implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        ArrayList<Card> row = gameManager.getRowFromCard(callerCard);
        System.out.println("len: " + row.size());
        for (int i = row.size() - 1; i >= 0; i--) {
            Card card = row.get(i);
            if (card.isBerserker()){
                System.out.println("dalghakas");
                card.getAbility().run(gameManager, card);
            }
        }
    }
}
