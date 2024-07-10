package mygdx.game.model.leader.skellige;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.leader.Leader;

public class KingBran extends Leader {
    public KingBran() {
        super("King Bran");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        // TODO : ye boolean is bran dar karta bezarim
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Skellige/" + getAssetName() + ".jpg";
    }
}
