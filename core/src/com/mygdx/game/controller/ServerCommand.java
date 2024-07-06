package com.mygdx.game.controller;

import java.io.Serializable;

public enum ServerCommand implements Serializable {
    DOES_USERNAME_EXIST,
    REGISTER_USER,
    FETCH_USER,
    FIND_USER_ACCOUNT,
    HAS_ACTIVE_SESSION,
    VALIDATE_PASSWORD,
    LOGIN_PLAYER,
    LOGOUT_PLAYER,

    SELECT_FACTION,
    SELECT_LEADER,
    SELECT_CARD,
    DE_SELECT_CARD,

    IS_ONLINE,
    START_GAME_REQUEST,

    CLOSE_CONNECTION,
    ;
}