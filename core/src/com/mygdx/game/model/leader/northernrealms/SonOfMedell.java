package com.mygdx.game.model.leader.northernrealms;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class SonOfMedell extends Leader {
    public SonOfMedell() {
        super("Son of Medell");
    }

    @Override
    public void run(GameManager gameManager) {
        
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.Toad);
        newCard.getAbility().run(gameManager, newCard);
        
        currentPlayer.setIsLeaderUsed(true);
    }
}
