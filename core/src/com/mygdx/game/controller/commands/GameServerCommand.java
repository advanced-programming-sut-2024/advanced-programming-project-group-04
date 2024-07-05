package com.mygdx.game.controller.commands;

public enum GameServerCommand {
    PASS_TURN,
    END_TURN,
    PLACE_CARD,
    PLACE_CARD_ENEMY,
    REMOVE_FROM_HAND,
    REMOVE_CARD,
    ADD_TO_HAND,
    ADD_A_LIFE_TO_ME,
    ADD_A_LIFE_TO_ENEMY,
    REMOVE_LIFE_FROM_ME,
    REMOVE_LIFE_FROM_ENEMY,

    EOF,
    ;
}
