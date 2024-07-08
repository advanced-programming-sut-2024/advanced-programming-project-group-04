package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.card.Card;

public interface Ability {
    void run(GameManager gameManager, Card callerCard);
}
