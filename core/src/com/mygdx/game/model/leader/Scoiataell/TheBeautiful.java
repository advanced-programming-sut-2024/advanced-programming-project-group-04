package com.mygdx.game.model.leader.Scoiataell;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class TheBeautiful extends Leader {
    public TheBeautiful() {
        super("The Beautiful");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.CommandersHorn);
        if (gameManager.canBePlacedToSpellRange(newCard)) {
            gameManager.placeCard(newCard , Position.SpellRange);
        }
        currentPlayer.setIsLeaderUsed(true);
    }
}
