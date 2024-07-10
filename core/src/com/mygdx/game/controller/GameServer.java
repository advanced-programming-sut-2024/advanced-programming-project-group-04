package mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mygdx.game.controller.commands.ClientCommand;
import mygdx.game.controller.commands.GameServerCommand;
import mygdx.game.controller.commands.GeneralCommand;
import mygdx.game.model.*;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;
import mygdx.game.model.faction.Monsters;
import mygdx.game.model.leader.Leader;
import mygdx.game.view.CustomTable;
import mygdx.game.view.GameMenu;
import mygdx.game.view.GraphicalCard;
import mygdx.game.view.TableSection;

import java.util.ArrayList;
import java.util.HashMap;

import static mygdx.game.controller.commands.GameClientCommand.*;

public class GameServer extends Thread {
    private boolean isMyTurn;

    Server mySession;
    Server enemySession;

    private final GameManager gameManager;

    public GameServer(Server mySession, Server enemySession) {
        this.mySession = mySession;
        this.enemySession = enemySession;
        this.isMyTurn = true;

        mySession.getPlayer().getDeck().shuffleIds();
        enemySession.getPlayer().getDeck().shuffleIds();
        this.gameManager = new GameManager(mySession.getPlayer(), enemySession.getPlayer(), this);
    }

    @Override
    public void run() {
        init();

        while (true) {
            if (mySession.isGameCommandReceived()) {
                System.out.println("GameServer received Command from me:");
                processCommand(mySession, enemySession, true);
                mySession.setGameCommandReceived(false);
            }
            if (enemySession.isGameCommandReceived()) {
                System.out.println("GameServer received Command from enemy:");
                processCommand(enemySession, mySession, false);
                enemySession.setGameCommandReceived(false);
            }
        }

    }

    private void init() {
        sendToBoth(ClientCommand.START_GAME);
        mySession.sendToClient(SET_IS_MY_TURN, true, EOF);
        System.out.println("Sending set is my turn to enemy");
        enemySession.sendToClient(SET_IS_MY_TURN, false, EOF);
        Player p1 = gameManager.getPlayer1().getPlayer();
        Player p2 = gameManager.getPlayer2().getPlayer();
        mySession.sendToClient(SET_FACTION, p1.getSelectedFaction(), EOF);
        enemySession.sendToClient(SET_FACTION, p2.getSelectedFaction(), EOF);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Leader.class, new Server.LeaderTypeAdapter());
        Gson gson = builder.create();
        mySession.sendToClient(SET_DECK, gson.toJson(p1.getDeck()), EOF);
        enemySession.sendToClient(SET_DECK, gson.toJson(p2.getDeck()), EOF);
        mySession.sendToClient(SET_LEADERS, p1.getDeck().getLeader(), p2.getDeck().getLeader(), EOF);
        enemySession.sendToClient(SET_LEADERS, p2.getDeck().getLeader(), p1.getDeck().getLeader(), EOF);
        mySession.sendToClient(SET_HANDS, gson.toJson(new Hand(gameManager.getCurrentPlayer().getHand())),
                gson.toJson(new Hand(gameManager.getOtherPlayer().getHand())), EOF);
        enemySession.sendToClient(SET_HANDS, gson.toJson(new Hand(gameManager.getOtherPlayer().getHand())),
                gson.toJson(new Hand(gameManager.getCurrentPlayer().getHand())), EOF);
        mySession.sendToClient(ADD_SOURCE, EOF);
        System.out.println("GameServer: Game initialized");
    }

    public void updateScores() {
//        gameMenu.updateScores(p1, p2);
        PlayerInGame p1 = gameManager.getPlayer1();
        PlayerInGame p2 = gameManager.getPlayer2();
        mySession.sendToClient(UPDATE_SCORES, p1, p2, EOF);
        enemySession.sendToClient(UPDATE_SCORES, p2, p1, EOF);
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

    public boolean placeCard(Card card, Position position) {
        System.out.println("GameServer placeCard 1");
        gameManager.placeCard(card, position);
        System.out.println("GameServer placeCard 2");
        gameManager.endTurn();
        System.out.println("GameServer placeCard 3");
        updateScores();
        System.out.println("GameServer placeCard 4");
        return true;
    }

    public boolean placeCardEnemy(Card card) {
        gameManager.placeCardEnemy(card);
        return true;
    }

    public boolean endTurn() {
        this.isMyTurn = !isMyTurn;
        gameManager.endTurn();
        return true;
    }

    public boolean canPlaceCard(Server server, Card card, Position position) {
        server.sendToClientVoid(gameManager.canPlaceCard(card, position));
        return false;
    }

    public boolean canPlaceCardEnemy(Server server, Card card, Position position) {
        server.sendToClientVoid(gameManager.canPlaceCardEnemy(card, position));
        return false;
    }

    public boolean removeFromHand(Card card, boolean isMyHand) {
        gameManager.removeFromHand(card, isMyHand ^ !isMyTurn);
        return true;
    }

    public boolean removeCard(Card card) {
        gameManager.removeCard(card);
        return true;
    }

    public boolean addToHand(Card card, boolean isMyHand) {
        System.out.println("isMyHand: " + isMyHand + " isMyTurn: " + isMyTurn + " result: " + (isMyHand ^ !isMyTurn));
        gameManager.addToHand(card, isMyHand ^ !isMyTurn);
        return true;
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
            mySession.sendToClient(ADD_CARD_TO_HAND, card, false, EOF);
            enemySession.sendToClient(ADD_CARD_TO_HAND, card, true, EOF);
        } else {
            mySession.sendToClient(ADD_CARD_TO_HAND, card, true, EOF);
            enemySession.sendToClient(ADD_CARD_TO_HAND, card, false, EOF);
        }
    }

    public void addCardToTableSection(Card card, Position position, boolean isEnemy) {
//        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
//        addCardToTable(card, gameMenu.getAllTables().get(tableSection));
        System.out.println("GameServer addCardToTableSection 1");
        mySession.sendToClient(ADD_CARD_TO_TABLE_SECTION, card, position, isEnemy ^ !isMyTurn, EOF);
        System.out.println("GameServer addCardToTableSection 2");
        enemySession.sendToClient(ADD_CARD_TO_TABLE_SECTION, card, position, isEnemy ^ isMyTurn, EOF);
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

    public boolean passTurn() {
        gameManager.endTurn();
        return true;
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
        System.out.println("Sent to me: " + inputs[0].toString());
        mySession.sendToClient(inputs);
        System.out.println("Sent to enemy " + inputs[0].toString());
        enemySession.sendToClient(inputs);
    }

    private void processCommand(Server mySession, Server enemySession, boolean isMe) {
        ArrayList<Object> inputs = mySession.getInputs();
        GameServerCommand command = (GameServerCommand) inputs.get(0);
        System.out.println(command.toString());
        boolean sendOutput;
        switch (command) {
            case IS_MY_TURN:
                mySession.sendToClientVoid(isMyTurn);
                sendOutput = false;
                break;
            case PASS_TURN:
                sendOutput = passTurn();
                break;
            case END_TURN:
                sendOutput = endTurn();
                break;
            case CAN_PLACE_CARD:
                sendOutput = canPlaceCard(mySession, (Card) inputs.get(1), (Position) inputs.get(2));
                break;
            case CAN_PLACE_CARD_ENEMY:
                sendOutput = canPlaceCardEnemy(mySession, (Card) inputs.get(1), (Position) inputs.get(2));
                break;
            case PLACE_CARD:
                sendOutput = placeCard((Card) inputs.get(1), (Position) inputs.get(2));
                break;
            case PLACE_CARD_ENEMY:
                sendOutput = placeCardEnemy((Card) inputs.get(1));
                break;
            case REMOVE_FROM_HAND:
                sendOutput = removeFromHand((Card) inputs.get(1), (boolean) inputs.get(2));
                break;
            case REMOVE_CARD:
                sendOutput = removeCard((Card) inputs.get(1));
                break;
            case ADD_TO_HAND:
                sendOutput = addToHand((Card) inputs.get(1), ((boolean) inputs.get(2)) ^ !isMe);
                break;

            case ACTIVATE_LEADER:
                ((Leader) inputs.get(1)).run(gameManager);
                sendOutput = true;
                break;

            default:
                sendOutput = false;
                break;
        }
        System.out.println("GameServer finished processing command");
        if (sendOutput) mySession.sendToClientVoid(GeneralCommand.CLEAR);
    }
}
