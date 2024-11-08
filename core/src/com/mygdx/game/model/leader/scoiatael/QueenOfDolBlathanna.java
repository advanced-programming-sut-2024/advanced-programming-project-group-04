package  mygdx.game.model.leader.scoiatael;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.card.AllCards;
import  mygdx.game.model.card.Card;
import  mygdx.game.model.leader.Leader;

public class QueenOfDolBlathanna extends Leader {
    public QueenOfDolBlathanna() {
        super("Queen of Dol Blathanna");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.Villentretenmerth);
        newCard.getAbility().run(gameManager, newCard);

        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Scoiatael/" + getAssetName() + ".jpg";
    }
}
    