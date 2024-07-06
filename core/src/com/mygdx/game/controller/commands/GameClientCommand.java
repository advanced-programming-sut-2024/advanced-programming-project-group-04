package com.mygdx.game.controller.commands;

import java.io.Serializable;

public enum GameClientCommand implements Serializable, Command {
    SET_IS_MY_TURN,
    SET_FACTION,
    SET_DECK,
    SET_LEADERS,

    UPDATE_SCORES,
    RESET_PASS_BUTTONS,
    REMOVE_FROM_VIEW,

    EOF
    ;
}
