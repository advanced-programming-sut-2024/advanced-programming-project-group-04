package mygdx.game.model.leader.monsters;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class KingOfTheWildHunt extends Leader {
    public KingOfTheWildHunt() {
        super("King of the Wild Hunt");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherplayer = gameManager.getOtherPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        // TODO : Like Medic
        Card newCard = new Card(AllCards.BirnaBran);
        newCard.getAbility().run(gameManager, newCard);

        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Monsters" + getAssetName() + ".jpg";
    }
}
