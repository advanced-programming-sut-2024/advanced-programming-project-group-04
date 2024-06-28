package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.view.CustomTable;
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

    public static boolean placeCard(Card card, TableSection tableSection) {
        boolean result;
        if (tableSection.isEnemy() ^ !isMyTurn) {
            result = gameManager.placeCardEnemy(card);
        }
        else {

            result = gameManager.placeCard(card, tableSection.getPosition());
            System.out.println(tableSection.getTitle());
        }
        System.out.println(isMyTurn);
        if (result) gameManager.endTurn();
        return result;
    }

    public static boolean canPlaceCardToPosition(Card card, TableSection tableSection) {
        Position position = tableSection.getPosition();
        if (tableSection.isEnemy() ^ !isMyTurn) return gameManager.canPlaceCardEnemy(card, position);
        else return gameManager.canPlaceCard(card, position);
    }

    public static void addCardToHand(Card card, PlayerInGame player) {
        if (player.equals(gameManager.getPlayer1())) addCardToTable(card, gameMenu.getAllTables().get(TableSection.MY_HAND));
        else addCardToTable(card, gameMenu.getAllTables().get(TableSection.ENEMY_HAND));
    }

    public static void addCardToTableSection(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        addCardToTable(card, gameMenu.getAllTables().get(tableSection));
    }

    public static void addCardToTable(Card card, Table table) {
        HashMap<Card, GraphicalCard> allCardsCreated = gameMenu.getAllCardsCreated();
        GraphicalCard graphicalCard = allCardsCreated.get(card);
        if (graphicalCard == null) graphicalCard = gameMenu.createNewGraphicalCard(card);
        table.add(graphicalCard);
    }

    public static void changeTurn() {
        isMyTurn = !isMyTurn;
        gameMenu.changeTurn(isMyTurn);
    }

    public static GraphicalCard removeCardFromView(Card card) {
        for (CustomTable table : gameMenu.getAllTables().values()) {
            GraphicalCard graphicalCard = removeCardFromTable(table, card);
            if (graphicalCard != null) return graphicalCard;
        }
        return null;
    }

    public static GraphicalCard removeCardFromView(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        CustomTable table = gameMenu.getAllTables().get(tableSection);
        return removeCardFromTable(table, card);
    }

    public static GraphicalCard removeCardFromTable(Table table, Card card) {
        for (Actor actor : table.getChildren()) {
            if (actor instanceof GraphicalCard && ((GraphicalCard)actor).getCard().equals(card)) {
                table.removeActor(actor);
                return (GraphicalCard)actor;
            }
        }
        return null;
    }

    public static void removeGraphicalCardFromTable(GraphicalCard graphicalCard, CustomTable table) {
        TableSection tableSection = table.getTableSection();
        Card card = graphicalCard.getCard();
        if (tableSection == TableSection.MY_HAND) gameManager.getPlayer1().removeFromHand(card);
        else if (tableSection == TableSection.ENEMY_HAND) gameManager.getPlayer2().removeFromHand(card);
        else if (tableSection.getPosition() != null) gameManager.removeCard(card);
    }

    public static void addGraphicalCardToTable(GraphicalCard graphicalCard, CustomTable table) {
        TableSection tableSection = table.getTableSection();
        Card card = graphicalCard.getCard();
        Position position = tableSection.getPosition();

        // TODO: check the bug where your cards can go to the enemy's hand

        if (tableSection == TableSection.MY_HAND) gameManager.getPlayer1().addToHand(card);
        else if (tableSection == TableSection.ENEMY_HAND) gameManager.getPlayer2().addToHand(card);
        else if (position != null){
            if (tableSection.isEnemy() ^ !isMyTurn) gameManager.placeCard(card, position);
            else gameManager.placeCardEnemy(card);
        }
    }

}
