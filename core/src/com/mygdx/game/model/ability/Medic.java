package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.card.Type;

import java.util.ArrayList;

public class Medic implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        ArrayList<Card> nonHeroGraveyard = new ArrayList<>();
        for(Card sampleCard : currentPlayer.getGraveyard()){
            if (!sampleCard.isHero() && sampleCard.isUnitCard()){
                nonHeroGraveyard.add(sampleCard);
            }
        }
        if(!nonHeroGraveyard.isEmpty()){
            Card selectedCard = gameManager.showSomeCardsAndSelectOne(nonHeroGraveyard);
            if(selectedCard!=null){
                if (selectedCard.getType().equals(Type.Agile)){
                    gameManager.placeCard(selectedCard , Position.Melee);
                } else {
                    gameManager.placeCard(selectedCard);
                }
            }
        }
    }
}
