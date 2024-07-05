package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.Monsters;
import com.mygdx.game.view.CustomTable;
import com.mygdx.game.view.GameMenu;
import com.mygdx.game.view.GraphicalCard;
import com.mygdx.game.view.TableSection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    public GameManager gameManager;
    public boolean isMyTurn;
    public GameMenu gameMenu;

    public GameController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public ArrayList<PlayerInGame> startNewGame(Player p1, Player p2) {
        gameManager = new GameManager(p1, p2, this);
        isMyTurn = true;
        ArrayList<PlayerInGame> output = new ArrayList<>();
        output.add(gameManager.getCurrentPlayer());
        output.add(gameManager.getOtherPlayer());
        return output;
    }

    public void setGameMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public void updateScores(PlayerInGame p1, PlayerInGame p2) {
        gameMenu.updateScores(p1, p2);
    }

    public void resetPassButtons() {
        gameMenu.resetPassedButtons();
    }

    public boolean placeCard(Card card, TableSection tableSection) {
        boolean result;
        if (tableSection.isEnemy() ^ !isMyTurn) {
            result = gameManager.placeCardEnemy(card);
        } else {

            result = gameManager.placeCard(card, tableSection.getPosition());
            System.out.println(tableSection.getTitle());
        }
        System.out.println(isMyTurn);

        if (result) gameManager.endTurn();
        if (isMyTurn) gameMenu.updateScores(gameManager.getCurrentPlayer(), gameManager.getOtherPlayer());
        else gameMenu.updateScores(gameManager.getOtherPlayer(), gameManager.getCurrentPlayer());

        return result;
    }

    public boolean canPlaceCardToPosition(Card card, TableSection tableSection) {
        Position position = tableSection.getPosition();
        if (tableSection.isEnemy() ^ !isMyTurn) return gameManager.canPlaceCardEnemy(card, position);
        else return gameManager.canPlaceCard(card, position);
    }

    public void addCardToHand(Card card, PlayerInGame player) {
        if (player.equals(gameManager.getPlayer1()))
            addCardToTable(card, gameMenu.getAllTables().get(TableSection.MY_HAND));
        else addCardToTable(card, gameMenu.getAllTables().get(TableSection.ENEMY_HAND));
    }

    public void addCardToTableSection(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        addCardToTable(card, gameMenu.getAllTables().get(tableSection));
    }

    public void addCardToTable(Card card, Table table) {
        HashMap<Card, GraphicalCard> allCardsCreated = gameMenu.getAllCardsCreated();
        GraphicalCard graphicalCard = allCardsCreated.get(card);
        if (graphicalCard == null) graphicalCard = gameMenu.createNewGraphicalCard(card);
        table.add(graphicalCard);
    }

    public void changeTurn() {
        isMyTurn = !isMyTurn;
        gameMenu.changeTurn(isMyTurn);
    }

    public GraphicalCard removeCardFromView(Card card) {
        for (CustomTable table : gameMenu.getAllTables().values()) {
            GraphicalCard graphicalCard = removeCardFromTable(table, card);
            if (graphicalCard != null) return graphicalCard;
        }
        return null;
    }

    public GraphicalCard removeCardFromView(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        CustomTable table = gameMenu.getAllTables().get(tableSection);
        return removeCardFromTable(table, card);
    }

    public GraphicalCard removeCardFromTable(Table table, Card card) {
        for (Actor actor : table.getChildren()) {
            if (actor instanceof GraphicalCard && ((GraphicalCard) actor).getCard().equals(card)) {
                table.removeActor(actor);
                return (GraphicalCard) actor;
            }
        }
        return null;
    }

    public void removeGraphicalCardFromTable(GraphicalCard graphicalCard, CustomTable table) {
        TableSection tableSection = table.getTableSection();
        Card card = graphicalCard.getCard();
        if (tableSection == TableSection.MY_HAND) gameManager.removeFromHand(card, isMyTurn);
        else if (tableSection == TableSection.ENEMY_HAND) gameManager.removeFromHand(card, !isMyTurn);
        else if (tableSection.getPosition() != null) gameManager.removeCard(card);
    }

    public void addGraphicalCardToTable(GraphicalCard graphicalCard, CustomTable table) {
        TableSection tableSection = table.getTableSection();
        Card card = graphicalCard.getCard();
        Position position = tableSection.getPosition();

        // TODO: check the bug where your cards can go to the enemy's hand

        if (tableSection == TableSection.MY_HAND) gameManager.addToHand(card, isMyTurn);
        else if (tableSection == TableSection.ENEMY_HAND) gameManager.addToHand(card, !isMyTurn);
        else if (position != null) {
            if (tableSection.isEnemy() ^ !isMyTurn) gameManager.placeCard(card, position);
            else gameManager.placeCardEnemy(card);
        }
    }

    public Card showSomeCardsAndSelectOne (ArrayList<Card> cards){
        return gameMenu.showSomeCardsAndSelectOne(cards).getCard();
    }

    public void passTurn() {
        gameManager.endTurn();
    }

    public void handleCheat(String cheatCode) {
        if (cheatCode.equals("Naddaf")) {
            System.out.println("<3<3<3<3");
        } else if (cheatCode.equals("Mohandes?")) {
            gameMenu.getMainInstance().cheraBenzinTamoomShod();
        } else if (cheatCode.equals("give me a life")) {
            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() + 1);
        } else if (cheatCode.equals("give enemy a life")) {
            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() + 1);
        } else if (cheatCode.equals("take a life away from me")) {
            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() - 1);
        } else if (cheatCode.equals("take a life away from enemy")) {
            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() - 1);
        } else if (cheatCode.startsWith("add card") && !cheatCode.contains("enemy")) {
            String[] parts = cheatCode.split(" ");
            String cardName = parts[2];
            AllCards allCard = null;
            try {
                allCard = AllCards.valueOf(cardName);
            } catch (IllegalArgumentException e) {
                System.out.println("Card not found: " + cardName);
            }
            Card card = new Card(allCard);

            if (card != null) {
                gameManager.getCurrentPlayer().addToHand(card);
                addCardToHand(card, gameManager.getCurrentPlayer());
            }
        } else if (cheatCode.startsWith("add card enemy")) {
            String[] parts = cheatCode.split(" ");
            String cardName = parts[3];
            AllCards allCard = null;
            try {
                allCard = AllCards.valueOf(cardName);
            } catch (IllegalArgumentException e) {
                System.out.println("Card not found: " + cardName);
            }
            Card card = new Card(allCard);

            if (card != null) {
                gameManager.getOtherPlayer().addToHand(card);
                addCardToHand(card, gameManager.getOtherPlayer());
            }
        } else if (cheatCode.equals("Besme Naddaf")) {
            for (Card card : gameManager.getAllCards()) {
                if (Monsters.getCards().contains(card)) {
                    gameManager.removeCard(card);
                    removeCardFromView(card);
                }
            }
        } else if (cheatCode.equals("nah i'd win")) {
            gameManager.getOtherPlayer().setRemainingLives(0);
            gameManager.getCurrentPlayer().setIsPassed(true);
            gameManager.getOtherPlayer().setIsPassed(true);
            gameManager.endTurn();
        } else if (cheatCode.equals("defeat")) {
            gameManager.getCurrentPlayer().setRemainingLives(0);
            gameManager.endTurn();
        } else if (cheatCode.startsWith("remove enemy")) {
            String[] parts = cheatCode.split(" ");
            String cardName = parts[2];
            AllCards allCard = null;
            try {
                allCard = AllCards.valueOf(cardName);
            } catch (IllegalArgumentException e) {
                System.out.println("Card not found: " + cardName);
            }
            ArrayList<Card> enemyCards = gameManager.getOtherPlayer().getAllCards();
            for (Card card : enemyCards) {
                if (card.getAllCard().equals(allCard)) {
                    gameManager.removeCard(card);
                    removeCardFromView(card);
                }
            }
        } else if (cheatCode.equals("end round")) {
            gameManager.getCurrentPlayer().setIsPassed(true);
            gameManager.getOtherPlayer().setIsPassed(true);
            gameManager.endTurn();
        }

    }

}
