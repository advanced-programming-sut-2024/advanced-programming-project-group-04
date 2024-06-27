package com.mygdx.game.model.leader.nilfgaard;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class TheWhiteFlame extends Leader {
    public TheWhiteFlame() {
        super("The White Flame");
    }

    @Override
    public void run(GameManager gameManager) {
        // ba farz in ke "daste kart" yani deck
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        for (Card sampleCard : currentPlayer.getDeckInGame()) {
            if (sampleCard.getAllCard().equals(AllCards.TorrentialRain)) {
                currentPlayer.removeFromDeckInGame(sampleCard);
                gameManager.placeCard(sampleCard);
                break;
            }
        }
        currentPlayer.setIsLeaderUsed(true);
    }
}
