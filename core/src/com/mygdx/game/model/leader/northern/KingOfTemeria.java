package com.mygdx.game.model.leader.northern;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class KingOfTemeria extends Leader {
    public KingOfTemeria() {
        super("King of Temeria");
    }

    @Override
    public void run(GameManager gameManager) {
        
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.CommandersHorn);
        if (gameManager.canBePlacedToSpellSiege(newCard)) {
            gameManager.placeCard(newCard , Position.SpellSiege);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

}
