package mygdx.game.model.leader.northernrealms;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class LordCommanderOfTheNorth extends Leader {
    public LordCommanderOfTheNorth() {
        super("Lord Commander of the North");
    }

    @Override
    public void run(GameManager gameManager) {

        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.Schirru);
        newCard.getAbility().run(gameManager, newCard);

        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/NorthernRealms/" + getAssetName() + ".jpg";
    }

}
