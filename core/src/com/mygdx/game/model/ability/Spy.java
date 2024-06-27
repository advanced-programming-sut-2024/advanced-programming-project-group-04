package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;

public class Spy implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        currentPlayer.addRandomCardToHandFromDeck();
        currentPlayer.addRandomCardToHandFromDeck();
    }
}
