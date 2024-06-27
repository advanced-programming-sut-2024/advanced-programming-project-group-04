package com.mygdx.game.model.leader.northern;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.leader.Leader;

public class TheSteelForged extends Leader {
    public TheSteelForged() {
        super("The Steel-Forged");
    }

    @Override
    public void run(GameManager gameManager) {
        
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.ClearWeather);
        gameManager.placeCard(newCard);
        currentPlayer.setIsLeaderUsed(true);
    }

}
