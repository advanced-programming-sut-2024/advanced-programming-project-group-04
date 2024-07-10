package mygdx.game.model.leader.monsters;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class DestroyerOfWorlds extends Leader {
    public DestroyerOfWorlds() {
        super("Destroyer of Worlds");
    }

    @Override
    public void run(GameManager gameManager) {
        // TODO: @Arman
//        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
//        if (currentPlayer.getIsLeaderUsed()) {
//            return;
//        }
//
//        if (currentPlayer.getHandCount() > 1) {
//            Card card1 = gameManager.showSomeCardsAndSelectOne(currentPlayer.getHand());
//            gameManager.removeFromHand(card1);
//            Card card2 = gameManager.showSomeCardsAndSelectOne(currentPlayer.getHand());
//            gameManager.removeFromHand(card2);
//            Card card3 = gameManager.showSomeCardsAndSelectOne(currentPlayer.getDeckInGame());
//            currentPlayer.removeFromDeckInGame(card3);
//            gameManager.addToHand(card3);
//        }
//        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Monsters" + getAssetName() + ".jpg";
    }
}
