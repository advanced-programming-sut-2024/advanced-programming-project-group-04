package com.mygdx.game.model.leader.northern;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class LordCommanderOfTheNorth extends Leader {
    public LordCommanderOfTheNorth() {
        super("Lord Commander of the North");
    }

    @Override
    public void run(GameManager gameManager) {
        
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.Schirru);
        newCard.getAbility().run(gameManager, newCard);
        
        currentPlayer.setIsLeaderUsed(true);
    }
}
