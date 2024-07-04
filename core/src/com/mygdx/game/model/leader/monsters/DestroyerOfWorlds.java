package com.mygdx.game.model.leader.monsters;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class DestroyerOfWorlds extends Leader {
    public DestroyerOfWorlds() {
        super("Destroyer of Worlds");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        if (currentPlayer.getHandCount() > 1) {
            Card card1 = gameManager.showSomeCardsAndSelectOne(currentPlayer.getHand());
            gameManager.removeFromHand(card1);
            Card card2 = gameManager.showSomeCardsAndSelectOne(currentPlayer.getHand());
            gameManager.removeFromHand(card2);
            Card card3 = gameManager.showSomeCardsAndSelectOne(currentPlayer.getDeckInGame());
            currentPlayer.removeFromDeckInGame(card3);
            gameManager.addToHand(card3);
        }
        currentPlayer.setIsLeaderUsed(true);
    }
}
