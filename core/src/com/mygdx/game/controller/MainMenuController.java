package com.mygdx.game.controller;

import com.mygdx.game.model.Player;

public class MainMenuController {
    private static Player currentPlayer;

    public static void setCurrentPlayer(Player player) { currentPlayer = player; }

    public static Player getCurrentPlayer() { return currentPlayer; }
}
