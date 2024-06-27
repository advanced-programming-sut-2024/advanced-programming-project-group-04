package com.mygdx.game.controller;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.view.GameMenu;
import com.mygdx.game.view.GraphicalCard;
import com.mygdx.game.view.TableSection;

import java.util.HashMap;

public class GameController {
    public static GameManager gameManager;
    public static boolean isMyTurn;
    public static GameMenu gameMenu;

    public static void startNewGame(Player p1, Player p2) {
        gameManager = new GameManager(p1, p2);
        isMyTurn = true;
    }

    public static void setGameMenu(GameMenu gameMenu) {
        GameController.gameMenu = gameMenu;
    }

    public static boolean placeCardController(Card card, TableSection tableSection) {
        boolean result;
        if (tableSection.isEnemy() ^ !isMyTurn) result = gameManager.placeCardEnemy(card);
        else result = gameManager.placeCard(card, tableSection.getPosition());
        System.out.println(isMyTurn);
        if (result) gameManager.endTurn();
        return result;
    }

    public static boolean canPlaceCardToPositionController(Card card, TableSection tableSection) {
        Position position = tableSection.getPosition();
        if (tableSection.isEnemy() ^ !isMyTurn) return gameManager.canPlaceCardEnemy(card, position);
        else return gameManager.canPlaceCard(card, tableSection.getPosition());
    }

    public static void addCardToTableSection(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        HashMap<Card, GraphicalCard> allCardsCreated = gameMenu.getAllCardsCreated();
        GraphicalCard graphicalCard = allCardsCreated.get(card);
        if (graphicalCard == null) graphicalCard = gameMenu.createNewGraphicalCard(card);
        gameMenu.getAllTables().get(tableSection).add(graphicalCard);
    }

    public static void changeTurn() {
        isMyTurn = !isMyTurn;
        gameMenu.changeTurn(isMyTurn);
    }
}
