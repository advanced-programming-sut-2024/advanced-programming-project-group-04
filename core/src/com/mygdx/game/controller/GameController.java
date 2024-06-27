package com.mygdx.game.controller;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.view.TableSection;

public class GameController {
    public static GameManager gameManager;
    public static boolean isMyTurn;

    public static void startNewGame(Player p1, Player p2) {
        gameManager = new GameManager(p1, p2);
        isMyTurn = true;
    }

    public static boolean placeCardController(Card card, TableSection tableSection) {
        boolean result;
        if (tableSection.isEnemy() ^ !isMyTurn) result = gameManager.placeCardEnemy(card);
        else result = gameManager.placeCard(card, tableSection.getPosition());
        System.out.println(isMyTurn);
        if (result) isMyTurn = !isMyTurn;
        return result;
    }
}
