package  mygdx.game.model.leader.nilfgaard;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.leader.Leader;

public class EmperorOfNilfgaard extends Leader {
    public EmperorOfNilfgaard() {
        super("Emperor of Nilfgaard");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherplayer = gameManager.getOtherPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        otherplayer.setIsLeaderUsed(true);
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Nilfgaard" + getAssetName() + ".jpg";
    }
}
