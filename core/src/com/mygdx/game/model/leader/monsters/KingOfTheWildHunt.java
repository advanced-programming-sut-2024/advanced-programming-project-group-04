package com.mygdx.game.model.leader.monsters;

import java.util.ArrayList;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
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
        
        ArrayList<Card> noneHeroFromGrave = new ArrayList<>();
        for (Card sampleCard : currentPlayer.getGraveyard()) {
            if (!sampleCard.isHero()){
                noneHeroFromGrave.add(sampleCard);
            }
        }

        if (noneHeroFromGrave.size() > 0) {
            int index = (int) (Math.random() * noneHeroFromGrave.size());
            Card card = noneHeroFromGrave.get(index);

            // TODO: fix this part for adding card to hand
            currentPlayer.removeFromGraveyard(card);
            gameManager.addToHand(card);
//            currentPlayer.addToHand(card);
        }

        
        
        currentPlayer.setIsLeaderUsed(true);
    }
}
