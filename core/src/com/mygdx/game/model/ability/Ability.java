package com.mygdx.game.model.ability;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.card.Card;

import java.io.Serializable;

public interface Ability extends Serializable {
    void run(GameManager gameManager , Card callerCard);
}
