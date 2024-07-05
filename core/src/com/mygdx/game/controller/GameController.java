package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.controller.commands.GameClientCommand;
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

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.controller.commands.GameServerCommand.*;

public class GameController {
    private boolean isMyTurn;
    public final GameMenu gameMenu;
    private Client client;

    public GameController(GameMenu gameMenu, Client client) {
        this.gameMenu = gameMenu;
        this.client = client;
    }

    public void setIsMyTurn(boolean isMyTurn) { this.isMyTurn = isMyTurn; }

    public void updateScores(int selfTotalHP, int enemyTotalHP) {
        gameMenu.updateScores(selfTotalHP, enemyTotalHP);
    }

    public void resetPassButtons() {
        gameMenu.resetPassedButtons();
    }

    public boolean placeCard(Card card, TableSection tableSection) {
        boolean result;
        if (tableSection.isEnemy() ^ !isMyTurn) {
            result = client.sendToServer(PLACE_CARD_ENEMY, card, EOF);
        } else {

            result = client.sendToServer(PLACE_CARD, card, tableSection.getPosition(), EOF);
            System.out.println(tableSection.getTitle());
        }
        System.out.println(isMyTurn);

        if (result) client.sendToServer(END_TURN, EOF);
        // TODO: @Arman send back update scores signal from the server
//        if (isMyTurn) gameMenu.updateScores(gameManager.getCurrentPlayer(), gameManager.getOtherPlayer());
//        else gameMenu.updateScores(gameManager.getOtherPlayer(), gameManager.getCurrentPlayer());

        return result;
    }

    public boolean canPlaceCardToPosition(Card card, TableSection tableSection) {
        Position position = tableSection.getPosition();
        if (tableSection.isEnemy() ^ !isMyTurn) return client.sendToServer(canPlaceCardEnemy, card, position);
        else return client.sendToServer(canPlaceCard, card, position);
    }

    public void addCardToHand(Card card, boolean isEnemy) {
        if (isEnemy) addCardToTable(card, gameMenu.getAllTables().get(TableSection.ENEMY_HAND));
        else addCardToTable(card, gameMenu.getAllTables().get(TableSection.MY_HAND));
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

    public void changeTurn(boolean isMyTurn) {
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
        if (tableSection == TableSection.MY_HAND) client.sendToServerVoid(REMOVE_FROM_HAND, card, isMyTurn, EOF);
        else if (tableSection == TableSection.ENEMY_HAND) client.sendToServerVoid(REMOVE_FROM_HAND, card, !isMyTurn, EOF);
        else if (tableSection.getPosition() != null) client.sendToServerVoid(REMOVE_CARD, card, EOF);
    }

    public void addGraphicalCardToTable(GraphicalCard graphicalCard, CustomTable table) {
        TableSection tableSection = table.getTableSection();
        Card card = graphicalCard.getCard();
        Position position = tableSection.getPosition();

        // TODO: check the bug where your cards can go to the enemy's hand

        if (tableSection == TableSection.MY_HAND) client.sendToServerVoid(ADD_TO_HAND, card, isMyTurn, EOF);
        else if (tableSection == TableSection.ENEMY_HAND) client.sendToServerVoid(ADD_TO_HAND, card, !isMyTurn, EOF);
        else if (position != null) {
            if (tableSection.isEnemy() ^ !isMyTurn) client.sendToServerVoid(PLACE_CARD, card, position, EOF);
            else client.sendToServerVoid(PLACE_CARD_ENEMY, card, EOF);
        }
    }

    public void passTurn() {
//        gameManager.endTurn();
        client.sendToServerVoid(PASS_TURN, EOF);
    }

    public boolean passButtonClicked() {

    }

    public void handleCheat(String cheatCode) {
        if (cheatCode.equals("Naddaf")) {
            System.out.println("<3<3<3<3");
        } else if (cheatCode.equals("Mohandes?")) {
            gameMenu.getMainInstance().cheraBenzinTamoomShod();
        } else if (cheatCode.equals("give me a life")) {
            client.sendToServerVoid(ADD_A_LIFE_TO_ME, EOF);
            // TODO: @Arman
//            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() + 1);
        } else if (cheatCode.equals("give enemy a life")) {
            client.sendToServerVoid(ADD_A_LIFE_TO_ENEMY, EOF);
            // TODO: @Arman
//            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() + 1);
        } else if (cheatCode.equals("take a life away from me")) {
            client.sendToServerVoid(REMOVE_LIFE_FROM_ME);
            // TODO: @Arman
//            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() - 1);
        } else if (cheatCode.equals("take a life away from enemy")) {
            client.sendToServerVoid(REMOVE_LIFE_FROM_ENEMY);
            // TODO: @Arman
//            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() - 1);
        } else if (cheatCode.startsWith("add card")) {
            // TODO @Matin check if the card is null
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
            client.sendToServer(END_TURN, EOF);
        } else if (cheatCode.equals("defeat")) {
            gameManager.getCurrentPlayer().setRemainingLives(0);
            client.sendToServer(END_TURN, EOF);
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
            client.sendToServer(END_TURN, EOF);
        }

    }

    public void processCommand(ArrayList<Object> inputs) {
        GameClientCommand command = (GameClientCommand) inputs.get(0);
        switch (command) {
            case SET_IS_MY_TURN:
                setIsMyTurn((boolean) inputs.get(1));
                break;
            case UPDATE_SCORES:
                updateScores((int) inputs.get(1), (int) inputs.get(2));
                break;
            case RESET_PASS_BUTTONS:
                resetPassButtons();
                break;

        }
    }

}
