package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.Card;

public class ClearWeather implements Ability {
    @Override
    public void run(GameManager gameManager, Card callerCard) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherPlayer = gameManager.getOtherPlayer();
        currentPlayer.setMeleeCardsIsWeather(false);
        otherPlayer.setMeleeCardsIsWeather(false);
        currentPlayer.setRangeCardsIsWeather(false);
        otherPlayer.setRangeCardsIsWeather(false);
        currentPlayer.setSiegeCardsIsWeather(false);
        otherPlayer.setSiegeCardsIsWeather(false);
        for (Card sampleCard : gameManager.getWeatherCards()) {
            gameManager.removeFromWeather(sampleCard);
        }
    }
}
