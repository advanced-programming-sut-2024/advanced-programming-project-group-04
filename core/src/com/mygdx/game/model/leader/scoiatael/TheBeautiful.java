package  mygdx.game.model.leader.scoiatael;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.Position;
import  mygdx.game.model.card.AllCards;
import  mygdx.game.model.card.Card;
import  mygdx.game.model.leader.Leader;

public class TheBeautiful extends Leader {
    public TheBeautiful() {
        super("The Beautiful");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.CommandersHorn);
        if (gameManager.canBePlacedToSpellRange(newCard)) {
            gameManager.placeCard(newCard , Position.SpellRange);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Scoiatael" + getAssetName() + ".jpg";
    }
}
