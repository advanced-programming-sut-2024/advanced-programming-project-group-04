package mygdx.game.model.leader.northernrealms;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.Position;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class KingOfTemeria extends Leader {
    public KingOfTemeria() {
        super("King of Temeria");
    }

    @Override
    public void run(GameManager gameManager) {

        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.CommandersHorn);
        if (gameManager.canBePlacedToSpellSiege(newCard)) {
            gameManager.placeCard(newCard, Position.SpellSiege);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/NorthernRealms" + getAssetName() + ".jpg";
    }

}
