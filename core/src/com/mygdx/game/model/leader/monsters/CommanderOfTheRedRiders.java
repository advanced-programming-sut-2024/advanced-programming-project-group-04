package mygdx.game.model.leader.monsters;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.Card;
import mygdx.game.model.card.Type;
import mygdx.game.model.leader.Leader;

import java.util.ArrayList;

public class CommanderOfTheRedRiders extends Leader {
    public CommanderOfTheRedRiders() {
        super("Commander of the Red Riders");
    }

    @Override
    public void run(GameManager gameManager) {
        // TODO: @Arman
//        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
//        if (currentPlayer.getIsLeaderUsed()) {
//            return;
//        }
//
//        ArrayList<Card> weatherCardsInDeckInGame = new ArrayList<>();
//        for (Card sampleCard : currentPlayer.getDeckInGame()) {
//            if (sampleCard.getType().equals(Type.Weather)) {
//                weatherCardsInDeckInGame.add(sampleCard);
//            }
//        }
//        if (!weatherCardsInDeckInGame.isEmpty()) {
//            Card selectedWeatherCard = gameManager.showSomeCardsAndSelectOne(weatherCardsInDeckInGame);
//            currentPlayer.removeFromDeckInGame(selectedWeatherCard);
//            gameManager.placeToWeather(selectedWeatherCard);
//        }
//        currentPlayer.setIsLeaderUsed(true);
    }
}
