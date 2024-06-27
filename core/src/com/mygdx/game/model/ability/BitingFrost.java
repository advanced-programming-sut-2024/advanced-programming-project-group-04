package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;

public class BitingFrost implements Ability {
    @Override
    public void run(GameManager gameManager , Card callerCard){
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherPlayer = gameManager.getOtherPlayer();
        currentPlayer.setMeleeCardsIsWeather(true);
        otherPlayer.setMeleeCardsIsWeather(true);
    }
}
