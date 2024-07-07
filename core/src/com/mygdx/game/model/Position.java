package com.mygdx.game.model;

import java.io.Serializable;

public enum Position implements Serializable {
    Melee,
    Siege,
    Range,
    SpellMelee,
    SpellSiege,
    SpellRange,
    WeatherPlace
    ;
    Position() {
    } 
}
