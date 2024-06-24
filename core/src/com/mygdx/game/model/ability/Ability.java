package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.card.Card;

public interface Ability {
    void run(GameManager gameManager , Card callerCard);
}
