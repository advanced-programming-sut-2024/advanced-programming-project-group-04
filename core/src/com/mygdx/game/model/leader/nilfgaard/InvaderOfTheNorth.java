package mygdx.game.model.leader.nilfgaard;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class InvaderOfTheNorth extends Leader {
    public InvaderOfTheNorth() {
        super("Invader of the North");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherplayer = gameManager.getOtherPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        currentPlayer.addRandomCardToHandFromGrave();
        otherplayer.addRandomCardToHandFromGrave();

        Card card = currentPlayer.drawRandomCardFromGraveyard();
        if (card != null) {
            currentPlayer.addToHand(card);
            currentPlayer.removeFromGraveyard(card);
            gameManager.addToHand(card, true);
        }
        Card anotherCard = otherplayer.drawRandomCardFromGraveyard();
        if (anotherCard != null) {
            otherplayer.addToHand(anotherCard);
            otherplayer.removeFromGraveyard(anotherCard);
            gameManager.addToHand(anotherCard, false);
        }

        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Nilfgaard" + getAssetName() + ".jpg";
    }
}
