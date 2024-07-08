package com.mygdx.game.controller.commands;

import java.io.Serializable;

public enum GameClientCommand implements Serializable, Command {
    SET_IS_MY_TURN,
    CHANGE_TURN,
    SET_FACTION,
    SET_DECK,
    SET_LEADERS,
    SET_HANDS,
    ADD_SOURCE,

    UPDATE_SCORES,
    RESET_PASS_BUTTONS,
    REMOVE_FROM_VIEW,

    ADD_CARD_TO_TABLE_SECTION,
    ADD_CARD_TO_HAND,

    EOF
    ;
}
