package com.mygdx.game.model.leader.monsters;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.card.Type;
import com.mygdx.game.model.leader.Leader;

import java.util.ArrayList;

public class CommanderOfTheRedRiders extends Leader {
    public CommanderOfTheRedRiders() {
        super("Commander of the Red Riders");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        ArrayList<Card> weatherCardsInDeckInGame = new ArrayList<>();
        for(Card sampleCard : currentPlayer.getDeckInGame()) {
            if (sampleCard.getType().equals(Type.Weather)) {
                weatherCardsInDeckInGame.add(sampleCard);
            }
        }
        if (!weatherCardsInDeckInGame.isEmpty()) {
            Card selectedWeatherCard = gameManager.showSomeCardsAndSelectOne(weatherCardsInDeckInGame);
            currentPlayer.removeFromDeckInGame(selectedWeatherCard);
            gameManager.placeToWeather(selectedWeatherCard);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Monsters/" + getAssetName() + ".jpg";
    }

}
