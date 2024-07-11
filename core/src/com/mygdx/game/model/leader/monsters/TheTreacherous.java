package mygdx.game.model.leader.monsters;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.ability.Spy;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

import java.util.ArrayList;

public class TheTreacherous extends Leader {
    public TheTreacherous() {
        super("The Treacherous");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        ArrayList<Card> spyCards = new ArrayList<Card>();
        for (Card sampleCard : gameManager.getAllCards()) {
            if (sampleCard.getAbility() instanceof Spy) {
                spyCards.add(sampleCard);
            }
        }
        // TODO : What the hell Should I do Here
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Monsters/" + getAssetName() + ".jpg";
    }
}
