package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.controller.commands.ClientCommand;
import com.mygdx.game.controller.commands.GameServerCommand;
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

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.controller.commands.GameClientCommand.*;

public class GameServer extends Thread {
    private boolean isMyTurn;

    Server mySession;
    Server enemySession;

    private final GameManager gameManager;

    public GameServer(Server mySession, Server enemySession) {
        this.mySession = mySession;
        this.enemySession = enemySession;
        this.isMyTurn = true;

        this.gameManager = new GameManager(mySession.getPlayer(), enemySession.getPlayer(), this);
    }

    @Override
    public void run() {
        init();

        while (true) {
            if (mySession.isGameCommandReceived()) {
                processCommand(mySession, enemySession, isMyTurn);
            }
            if (enemySession.isGameCommandReceived()) {
                processCommand(enemySession, mySession, !isMyTurn);
            }
        }

    }

    private void init() {
        sendToBoth(ClientCommand.START_GAME);
        mySession.sendToClientVoid(SET_IS_MY_TURN, true, EOF);
        enemySession.sendToClientVoid(SET_IS_MY_TURN, false, EOF);
        Player p1 = gameManager.getPlayer1().getPlayer();
        Player p2 = gameManager.getPlayer2().getPlayer();
        mySession.sendToClientVoid(SET_FACTION, p1.getSelectedFaction(), EOF);
        enemySession.sendToClientVoid(SET_FACTION, p2.getSelectedFaction(), EOF);
        mySession.sendToClientVoid(SET_DECK, p1.getDeck(), EOF);
        enemySession.sendToClientVoid(SET_DECK, p1.getDeck(), EOF);
        mySession.sendToClientVoid(SET_LEADERS, p1.getDeck().getLeader(), p2.getDeck().getLeader(), EOF);
        enemySession.sendToClientVoid(SET_LEADERS, p2.getDeck().getLeader(), p1.getDeck().getLeader(), EOF);
        mySession.sendToClientVoid(SET_HANDS, gameManager.getCurrentPlayer().getHand(), gameManager.getOtherPlayer().getHand(), EOF);
        enemySession.sendToClientVoid(SET_HANDS, gameManager.getOtherPlayer().getHand(), gameManager.getCurrentPlayer().getHand(), EOF);
    }

    public void updateScores() {
//        gameMenu.updateScores(p1, p2);
        PlayerInGame p1 = gameManager.getPlayer1();
        PlayerInGame p2 = gameManager.getPlayer2();
        mySession.sendToClientVoid(UPDATE_SCORES, p1.getTotalHP(), p2.getTotalHP(), EOF);
        enemySession.sendToClientVoid(UPDATE_SCORES, p2.getTotalHP(), p1.getTotalHP(), EOF);
    }

    public void resetPassButtons() {
//        gameMenu.resetPassedButtons();
        sendToBoth(RESET_PASS_BUTTONS, EOF);
    }

//    public boolean placeCard(Card card, TableSection tableSection) {
//        boolean result;
//        if (tableSection.isEnemy() ^ !isMyTurn) {
//            result = gameManager.placeCardEnemy(card);
//        } else {
//
//            result = gameManager.placeCard(card, tableSection.getPosition());
//            System.out.println(tableSection.getTitle());
//        }
//        System.out.println(isMyTurn);
//
//        if (result) gameManager.endTurn();
//        if (isMyTurn) gameMenu.updateScores(gameManager.getCurrentPlayer(), gameManager.getOtherPlayer());
//        else gameMenu.updateScores(gameManager.getOtherPlayer(), gameManager.getCurrentPlayer());

//        return result;
//    }

    public void placeCard(Card card) {
        gameManager.placeCard(card);
    }

    public void placeCardEnemy(Card card) {
        gameManager.placeCardEnemy(card);
    }

    public void endTurn() {
        gameManager.endTurn();
    }

    public void canPlaceCard(Server server, Card card, Position position) {
        server.sendToClientVoid(gameManager.canPlaceCard(card, position));
    }

    public void canPlaceCardEnemy(Server server, Card card, Position position) {
        server.sendToClientVoid(gameManager.canPlaceCardEnemy(card, position));
    }

    public void removeFromHand(Card card, boolean isMyTurn) {
        gameManager.removeFromHand(card, isMyTurn);
    }

    public void removeCard(Card card) {
        gameManager.removeCard(card);
    }

    public void addToHand(Card card, boolean isMyTurn) {
        gameManager.addToHand(card, isMyTurn);
    }

//    public boolean canPlaceCardToPosition(Card card, TableSection tableSection) {
//        Position position = tableSection.getPosition();
//        if (tableSection.isEnemy() ^ !isMyTurn) return gameManager.canPlaceCardEnemy(card, position);
//        else return gameManager.canPlaceCard(card, position);
//    }

    public void addCardToHand(Card card, PlayerInGame player) {
//        if (player.equals(gameManager.getPlayer1()))
//            addCardToTable(card, gameMenu.getAllTables().get(TableSection.MY_HAND));
//        else addCardToTable(card, gameMenu.getAllTables().get(TableSection.ENEMY_HAND));
        if (player.getPlayer().equals(mySession.getPlayer())) {
            mySession.sendToClientVoid(ADD_CARD_TO_HAND, card, true, EOF);
            enemySession.sendToClientVoid(ADD_CARD_TO_HAND, card, false, EOF);
        } else {
            mySession.sendToClientVoid(ADD_CARD_TO_HAND, card, false, EOF);
            enemySession.sendToClientVoid(ADD_CARD_TO_HAND, card, true, EOF);
        }
    }

    public void addCardToTableSection(Card card, Position position, boolean isEnemy) {
//        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
//        addCardToTable(card, gameMenu.getAllTables().get(tableSection));
        mySession.sendToClientVoid(ADD_CARD_TO_TABLE_SECTION, card, position, isEnemy ^ !isMyTurn, EOF);
        enemySession.sendToClientVoid(ADD_CARD_TO_TABLE_SECTION, card, position, isEnemy ^ isMyTurn, EOF);
    }

//    public void addCardToTable(Card card, Table table) {
//        HashMap<Card, GraphicalCard> allCardsCreated = gameMenu.getAllCardsCreated();
//        GraphicalCard graphicalCard = allCardsCreated.get(card);
//        if (graphicalCard == null) graphicalCard = gameMenu.createNewGraphicalCard(card);
//        table.add(graphicalCard);
//    }

    public void changeTurn() {
        isMyTurn = !isMyTurn;
        sendToBoth(CHANGE_TURN, EOF);
//        gameMenu.changeTurn(isMyTurn);
    }

    public void removeCardFromView(Card card) {
        sendToBoth(REMOVE_FROM_VIEW, card, EOF);
    }

//    public GraphicalCard removeCardFromView(Card card, Position position, boolean isEnemy) {
//        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
//        CustomTable table = gameMenu.getAllTables().get(tableSection);
//        return removeCardFromTable(table, card);
//    }

//    public GraphicalCard removeCardFromTable(Table table, Card card) {
//        for (Actor actor : table.getChildren()) {
//            if (actor instanceof GraphicalCard && ((GraphicalCard) actor).getCard().equals(card)) {
//                table.removeActor(actor);
//                return (GraphicalCard) actor;
//            }
//        }
//        return null;
//    }

//    public void removeGraphicalCardFromTable(GraphicalCard graphicalCard, CustomTable table) {
//        TableSection tableSection = table.getTableSection();
//        Card card = graphicalCard.getCard();
//        if (tableSection == TableSection.MY_HAND) gameManager.removeFromHand(card, isMyTurn);
//        else if (tableSection == TableSection.ENEMY_HAND) gameManager.removeFromHand(card, !isMyTurn);
//        else if (tableSection.getPosition() != null) gameManager.removeCard(card);
//    }

//    public void addGraphicalCardToTable(GraphicalCard graphicalCard, CustomTable table) {
//        TableSection tableSection = table.getTableSection();
//        Card card = graphicalCard.getCard();
//        Position position = tableSection.getPosition();
//
//        // TODO: check the bug where your cards can go to the enemy's hand
//
//        if (tableSection == TableSection.MY_HAND) gameManager.addToHand(card, isMyTurn);
//        else if (tableSection == TableSection.ENEMY_HAND) gameManager.addToHand(card, !isMyTurn);
//        else if (position != null) {
//            if (tableSection.isEnemy() ^ !isMyTurn) gameManager.placeCard(card, position);
//            else gameManager.placeCardEnemy(card);
//        }
//    }

    public void passTurn() {
        gameManager.endTurn();
    }

//    public void handleCheat(String cheatCode) {
//        if (cheatCode.equals("Naddaf")) {
//            System.out.println("<3<3<3<3");
//        } else if (cheatCode.equals("Mohandes?")) {
//            gameMenu.getMainInstance().cheraBenzinTamoomShod();
//        } else if (cheatCode.equals("give me a life")) {
//            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() + 1);
//        } else if (cheatCode.equals("give enemy a life")) {
//            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() + 1);
//        } else if (cheatCode.equals("take a life away from me")) {
//            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() - 1);
//        } else if (cheatCode.equals("take a life away from enemy")) {
//            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() - 1);
//        } else if (cheatCode.startsWith("add card")) {
//            String[] parts = cheatCode.split(" ");
//            String cardName = parts[2];
//            AllCards allCard = null;
//            try {
//                allCard = AllCards.valueOf(cardName);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Card not found: " + cardName);
//            }
//            Card card = new Card(allCard);
//
//            if (card != null) {
//                gameManager.getCurrentPlayer().addToHand(card);
//                addCardToHand(card, gameManager.getCurrentPlayer());
//            }
//        } else if (cheatCode.equals("Besme Naddaf")) {
//            for (Card card : gameManager.getAllCards()) {
//                if (Monsters.getCards().contains(card)) {
//                    gameManager.removeCard(card);
//                    removeCardFromView(card);
//                }
//            }
//        } else if (cheatCode.equals("nah i'd win")) {
//            gameManager.getOtherPlayer().setRemainingLives(0);
//            gameManager.getCurrentPlayer().setIsPassed(true);
//            gameManager.getOtherPlayer().setIsPassed(true);
//            gameManager.endTurn();
//        } else if (cheatCode.equals("defeat")) {
//            gameManager.getCurrentPlayer().setRemainingLives(0);
//            gameManager.endTurn();
//        } else if (cheatCode.startsWith("remove enemy")) {
//            String[] parts = cheatCode.split(" ");
//            String cardName = parts[2];
//            AllCards allCard = null;
//            try {
//                allCard = AllCards.valueOf(cardName);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Card not found: " + cardName);
//            }
//            ArrayList<Card> enemyCards = gameManager.getOtherPlayer().getAllCards();
//            for (Card card : enemyCards) {
//                if (card.getAllCard().equals(allCard)) {
//                    gameManager.removeCard(card);
//                    removeCardFromView(card);
//                }
//            }
//        } else if (cheatCode.equals("end round")) {
//            gameManager.getCurrentPlayer().setIsPassed(true);
//            gameManager.getOtherPlayer().setIsPassed(true);
//            gameManager.endTurn();
//        }
//
//    }

    private void sendToBoth(Object... inputs) {
        System.out.println("Sent to me");
        mySession.sendToClientVoid(inputs);
        System.out.println("Sent to enemy");
        enemySession.sendToClientVoid(inputs);
    }

    private void processCommand(Server mySession, Server enemySession, boolean isMyTurn) {
        ArrayList<Object> inputs = mySession.getInputs();
        GameServerCommand command = (GameServerCommand) inputs.get(0);
        switch (command) {
            case IS_MY_TURN:
                mySession.sendToClientVoid(isMyTurn);
                break;
            case PASS_TURN:
                passTurn();
                break;
            case END_TURN:
                endTurn();
                break;
            case CAN_PLACE_CARD:
                canPlaceCard(mySession, (Card) inputs.get(1), (Position) inputs.get(2));
                break;
            case CAN_PLACE_CARD_ENEMY:
                canPlaceCardEnemy(mySession, (Card) inputs.get(1), (Position) inputs.get(2));
                break;
            case PLACE_CARD:
                placeCard((Card) inputs.get(1));
                break;
            case PLACE_CARD_ENEMY:
                placeCardEnemy((Card) inputs.get(1));
                break;
            case REMOVE_FROM_HAND:
                removeFromHand((Card) inputs.get(1), isMyTurn);
                break;
            case REMOVE_CARD:
                removeCard((Card) inputs.get(1));
                break;
            case ADD_TO_HAND:
                addToHand((Card) inputs.get(1), isMyTurn);
                break;
        }
    }
}
