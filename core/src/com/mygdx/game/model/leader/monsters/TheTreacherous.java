package com.mygdx.game.model.leader.monsters;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.ability.Ability;
import com.mygdx.game.model.ability.Spy;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.card.Type;
import com.mygdx.game.model.leader.Leader;

import java.util.ArrayList;

public class TheTreacherous extends Leader{
    public TheTreacherous() {
        super("The Treacherous");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        ArrayList<Card> spyCards = new ArrayList<Card>();
        for (Card sampleCard : gameManager.getAllCards()) {
            if (sampleCard.getAbility() instanceof Spy){
                spyCards.add(sampleCard);
            }
        }
        // TODO : What the hell Should I do Here
        currentPlayer.setIsLeaderUsed(true);
    }
}
