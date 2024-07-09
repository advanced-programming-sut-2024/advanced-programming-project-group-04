package mygdx.game.controller.commands;

import java.io.Serializable;

public enum GameServerCommand implements Serializable, Command {
    IS_MY_TURN,
    PASS_TURN,
    END_TURN,
    CAN_PLACE_CARD,
    CAN_PLACE_CARD_ENEMY,
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
