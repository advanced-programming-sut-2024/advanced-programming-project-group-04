package mygdx.game.model.ability;

import mygdx.game.model.GameManager;
import mygdx.game.model.card.Card;

import java.io.Serializable;

public interface Ability extends Serializable {
    void run(GameManager gameManager , Card callerCard);
}
