package mygdx.game.model.ability;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.card.Card;

public class Spy implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        gameManager.drawRandomCardFromDeck(2);
    }
}
