package com.mygdx.game.model.card;

import java.io.Serializable;

public enum Type implements Serializable {
    CloseCombat,
    Agile,
    RangedCombat,
    Siege,
    Spell,
    Weather;
    Type() {
    }
}
