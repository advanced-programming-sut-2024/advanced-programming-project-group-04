package mygdx.game.model.leader.nilfgaard;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.leader.Leader;

public class HisImperialMajesty extends Leader {
    public HisImperialMajesty() {
        super("His Imperial Majesty");
    }

    @Override
    public void run(GameManager gameManager) {

        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        PlayerInGame otherPlayer = gameManager.getOtherPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        // TODO @Arman : Chetori SHOW konim?

        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Nilfgaard" + getAssetName() + ".jpg";
    }
}
