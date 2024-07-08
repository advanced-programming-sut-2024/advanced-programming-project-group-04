package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.Card;

public class SkelligeStrom implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherPlayer = gameManager.getOtherPlayer();
        currentPlayer.setSiegeCardsIsWeather(true);
        otherPlayer.setSiegeCardsIsWeather(true);
        currentPlayer.setRangeCardsIsWeather(true);
        otherPlayer.setRangeCardsIsWeather(true);
    }
}
