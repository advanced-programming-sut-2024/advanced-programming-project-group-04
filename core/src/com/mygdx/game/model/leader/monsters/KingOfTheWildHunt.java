package com.mygdx.game.model.leader.monsters;

import java.util.ArrayList;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class KingOfTheWildHunt extends Leader {
    public KingOfTheWildHunt() {
        super("King of the Wild Hunt");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherplayer = gameManager.getOtherPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        // TODO : Like Medic
        Card newCard = new Card(AllCards.BirnaBran);
        newCard.getAbility().run(gameManager, newCard);
        
        currentPlayer.setIsLeaderUsed(true);
    }
}
