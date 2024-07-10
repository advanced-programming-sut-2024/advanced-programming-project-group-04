package  mygdx.game.model.leader.nilfgaard;

import  mygdx.game.model.GameManager;
import  mygdx.game.model.PlayerInGame;
import  mygdx.game.model.card.AllCards;
import  mygdx.game.model.card.Card;
import  mygdx.game.model.leader.Leader;

public class TheRelentless extends Leader {
    public TheRelentless() {
        super("The Relentless");
    }

    @Override
    public void run(GameManager gameManager) {
        PlayerInGame currentPlayer = gameManager.getCurrentPlayer();

        if (currentPlayer.getIsLeaderUsed()) {
            return;
        }
        
        // TODO : is this Ability the same with medic?
        Card newCard = new Card(AllCards.BirnaBran);
        newCard.getAbility().run(gameManager, newCard);

        currentPlayer.setIsLeaderUsed(true);
    }

    @Override
    public String getImageURL() {
        return "images/factions/Nilfgaard" + getAssetName() + ".jpg";
    }
}
