package com.mygdx.game.model.leader.monsters;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class BringerOfDeath extends Leader{
    public BringerOfDeath() {
        super("Bringer Of Death");
    }

    @Override
    public void run(GameManager gameManager) {
        
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.CommandersHorn);
        if (gameManager.canBePlacedToSpellMelee(newCard)) {
            gameManager.placeCard(newCard , Position.SpellMelee);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

}
