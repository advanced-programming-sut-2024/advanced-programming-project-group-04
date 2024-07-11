package mygdx.game.model.leader.nilfgaard;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class TheWhiteFlame extends Leader {
    public TheWhiteFlame() {
        super("The White Flame");
    }

    @Override
    public void run(GameManager gameManager) {
        // ba farz in ke "daste kart" yani deck
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        for (Card sampleCard : currentPlayer.getDeckInGame()) {
            if (sampleCard.getAllCard().equals(AllCards.TorrentialRain)) {
                currentPlayer.removeFromDeckInGame(sampleCard);
                gameManager.placeCard(sampleCard);
                break;
            }
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Nilfgaard/" + getAssetName() + ".jpg";
    }
}
