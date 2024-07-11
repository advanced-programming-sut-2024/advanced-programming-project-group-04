package  mygdx.game.model.leader.scoiatael;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.card.AllCards;
import  mygdx.game.model.card.Card;
import  mygdx.game.model.leader.Leader;

public class PurebloodElf extends Leader {
    public PurebloodElf() {
        super("Pureblood Elf");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        for (Card sampleCard : currentPlayer.getDeckInGame()) {
            if (sampleCard.getAllCard().equals(AllCards.BitingFrost)) {
                currentPlayer.removeFromDeckInGame(sampleCard);
                gameManager.placeCard(sampleCard);
                break;
            }
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Scoiatael/" + getAssetName() + ".jpg";
    }
}
