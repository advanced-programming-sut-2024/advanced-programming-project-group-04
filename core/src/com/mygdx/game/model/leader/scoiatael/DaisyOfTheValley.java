package  mygdx.game.model.leader.scoiatael;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.leader.Leader;

public class DaisyOfTheValley extends Leader {
    public DaisyOfTheValley() {
        super("Daisy of the Valley");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        // TODO : This leader is Kinda Wierd
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Scoiatael/" + getAssetName() + ".jpg";
    }
}
