package mygdx.game.model.leader.northernrealms;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

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

    @Override
    public String getImageURL() {
        return "images/factions/NorthernRealms" + getAssetName() + ".jpg";
    }
}
