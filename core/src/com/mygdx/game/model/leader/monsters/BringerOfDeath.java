package mygdx.game.model.leader.monsters;

import mygdx.game.model.GameManager;
import mygdx.game.model.PlayerInGame;
import mygdx.game.model.Position;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.leader.Leader;

public class BringerOfDeath extends Leader {
    public BringerOfDeath() {
        super("Bringer Of Death");
    }

    @Override
    public void run(GameManager gameManager) {

        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();
        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }

        Card newCard = new Card(AllCards.CommandersHorn);
        if (gameManager.canBePlacedToSpellMelee(newCard)) {
            gameManager.placeCard(newCard, Position.SpellMelee);
        }
        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/leaders/Monsters/" + getAssetName() + ".jpg";
    }
}
