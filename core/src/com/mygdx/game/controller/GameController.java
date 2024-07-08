package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.controller.commands.GameClientCommand;
import com.mygdx.game.model.Deck;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.Position;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.Faction;
import com.mygdx.game.model.faction.Monsters;
import com.mygdx.game.model.leader.Leader;
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
    private Player player;

    public GameController(GameMenu gameMenu, Client client, Player player) {
        this.gameMenu = gameMenu;
        this.client = client;
        this.player = player;
    }

    public boolean isMyTurn() { return this.isMyTurn; }

    public boolean setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
        return true;
    }

    public boolean updateScores(int selfTotalHP, int enemyTotalHP) {
        gameMenu.updateScores(selfTotalHP, enemyTotalHP);
        return true;
    }

    public boolean resetPassButtons() {
        gameMenu.resetPassedButtons();
        return true;
    }

    public boolean placeCard(Card card, TableSection tableSection) {
        boolean result;
        if (tableSection.isEnemy()) {
            result = client.sendToServer(PLACE_CARD_ENEMY, card, EOF);
        } else {
            result = client.sendToServer(PLACE_CARD, card, tableSection.getPosition(), EOF);
            System.out.println(tableSection.getTitle());
        }

        if (result) client.sendToServer(END_TURN, EOF);
        // TODO: @Arman send back update scores signal from the server
//        if (isMyTurn) gameMenu.updateScores(gameManager.getCurrentPlayer(), gameManager.getOtherPlayer());
//        else gameMenu.updateScores(gameManager.getOtherPlayer(), gameManager.getCurrentPlayer());

        return result;
    }

    public boolean canPlaceCardToPosition(Card card, TableSection tableSection) {
        Position position = tableSection.getPosition();
        if (tableSection.isEnemy() ^ !isMyTurn) return client.sendToServer(CAN_PLACE_CARD_ENEMY, card, position, EOF);
        else return client.sendToServer(CAN_PLACE_CARD, card, position, EOF);
    }

    public boolean addCardToHand(Card card, boolean isEnemy) {
        if (isEnemy) addCardToTable(card, gameMenu.getAllTables().get(TableSection.ENEMY_HAND));
        else addCardToTable(card, gameMenu.getAllTables().get(TableSection.MY_HAND));
        return true;
    }

    public boolean addCardToTableSection(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        addCardToTable(card, gameMenu.getAllTables().get(tableSection));
        return true;
    }

    public void addCardToTable(Card card, Table table) {
        HashMap<Card, GraphicalCard> allCardsCreated = gameMenu.getAllCardsCreated();
        GraphicalCard graphicalCard = allCardsCreated.get(card);
        if (graphicalCard == null) graphicalCard = gameMenu.createNewGraphicalCard(card);
        table.add(graphicalCard);
    }

    public boolean changeTurn() {
        this.isMyTurn = !isMyTurn;
        gameMenu.changeTurn(isMyTurn);
        return true;
    }

    public boolean removeCardFromView(Card card) {
        System.out.println("GameController removeCardFromView 1");
        for (CustomTable table : gameMenu.getAllTables().values()) {
            removeCardFromTable(table, card);
        }
        return true;
    }

    public GraphicalCard removeCardFromView(Card card, Position position, boolean isEnemy) {
        TableSection tableSection = TableSection.getTableSectionByPosition(position, isEnemy ^ !isMyTurn);
        CustomTable table = gameMenu.getAllTables().get(tableSection);
        return removeCardFromTable(table, card);
    }

    public GraphicalCard removeCardFromTable(Table table, Card card) {
        System.out.println("GameController removeCardFromTable 1");
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
        if (tableSection == TableSection.MY_HAND) client.sendToServer(REMOVE_FROM_HAND, card, isMyTurn, EOF);
        else if (tableSection == TableSection.ENEMY_HAND) client.sendToServer(REMOVE_FROM_HAND, card, !isMyTurn, EOF);
        else if (tableSection.getPosition() != null) client.sendToServer(REMOVE_CARD, card, EOF);
    }

    public void addGraphicalCardToTable(GraphicalCard graphicalCard, CustomTable table) {
        TableSection tableSection = table.getTableSection();
        Card card = graphicalCard.getCard();
        Position position = tableSection.getPosition();

        // TODO: check the bug where your cards can go to the enemy's hand

        if (tableSection == TableSection.MY_HAND) client.sendToServer(ADD_TO_HAND, card, EOF);
        else if (tableSection == TableSection.ENEMY_HAND) {
//            client.sendToServer(ADD_TO_HAND, card, false, EOF);
            throw new RuntimeException("Can't add card to enemy hand");
        }
        else if (position != null) {
            if (tableSection.isEnemy() ^ !isMyTurn) client.sendToServer(PLACE_CARD, card, position, EOF);
            else client.sendToServer(PLACE_CARD_ENEMY, card, EOF);
        }
    }

    public void passTurn() {
//        gameManager.endTurn();
        client.sendToServer(PASS_TURN, EOF);
    }

    public boolean passButtonClicked() {
        if (client.sendToServer(IS_MY_TURN, EOF)) {
            client.sendToServer(PASS_TURN, EOF);
            return true;
        } else return false;
    }

    public void handleCheat(String cheatCode) {
//        if (cheatCode.equals("Naddaf")) {
//            System.out.println("<3<3<3<3");
//        } else if (cheatCode.equals("Mohandes?")) {
//            gameMenu.getMainInstance().cheraBenzinTamoomShod();
//        } else if (cheatCode.equals("give me a life")) {
//            client.sendToServer(ADD_A_LIFE_TO_ME, EOF);
//            // TODO: @Arman
////            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() + 1);
//        } else if (cheatCode.equals("give enemy a life")) {
//            client.sendToServer(ADD_A_LIFE_TO_ENEMY, EOF);
//            // TODO: @Arman
////            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() + 1);
//        } else if (cheatCode.equals("take a life away from me")) {
//            client.sendToServer(REMOVE_LIFE_FROM_ME);
//            // TODO: @Arman
////            gameManager.getCurrentPlayer().setRemainingLives(gameManager.getCurrentPlayer().getRemainingLives() - 1);
//        } else if (cheatCode.equals("take a life away from enemy")) {
//            client.sendToServer(REMOVE_LIFE_FROM_ENEMY);
//            // TODO: @Arman
////            gameManager.getOtherPlayer().setRemainingLives(gameManager.getOtherPlayer().getRemainingLives() - 1);
//        } else if (cheatCode.startsWith("add card")) {
//            // TODO @Matin check if the card is null
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
////                gameManager.getCurrentPlayer().addToHand(card);
//                client.sendToServer(ADD_TO_HAND, card, false, EOF);
//                addCardToHand(card, false);
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
//            client.sendToServer(END_TURN, EOF);
//        } else if (cheatCode.equals("defeat")) {
//            gameManager.getCurrentPlayer().setRemainingLives(0);
//            client.sendToServer(END_TURN, EOF);
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
//            client.sendToServer(END_TURN, EOF);
//        }
//
    }

    public void processCommand(ArrayList<Object> inputs) {
        GameClientCommand command = (GameClientCommand) inputs.get(0);
        System.out.println("GameController received: " + command.toString());
        boolean sendOutput;
        switch (command) {
            case SET_IS_MY_TURN:
                sendOutput = setIsMyTurn((boolean) inputs.get(1));
                break;
            case CHANGE_TURN:
                sendOutput = changeTurn();
                break;
            case SET_FACTION:
                sendOutput = setFaction((Faction) inputs.get(1));
                break;
            case SET_DECK:
                sendOutput = setDeck((Deck) inputs.get(1));
                break;
            case SET_LEADERS:
                sendOutput = setLeaders((Leader) inputs.get(1), (Leader) inputs.get(2));
                break;
            case SET_HANDS:
                sendOutput = setHands((ArrayList<Card>) inputs.get(1), (ArrayList<Card>) inputs.get(2));
                break;
            case ADD_SOURCE:
                gameMenu.addSource();
                sendOutput = true;
                break;
            case UPDATE_SCORES:
                sendOutput = updateScores((int) inputs.get(1), (int) inputs.get(2));
                break;
            case RESET_PASS_BUTTONS:
                sendOutput = resetPassButtons();
                break;
            case REMOVE_FROM_VIEW:
                sendOutput = removeCardFromView((Card) inputs.get(1));
                break;
            case ADD_CARD_TO_TABLE_SECTION:
                sendOutput = addCardToTableSection((Card) inputs.get(1), (Position) inputs.get(2), (boolean) inputs.get(3));
                break;
            case ADD_CARD_TO_HAND:
                sendOutput = addCardToHand((Card) inputs.get(1), (boolean) inputs.get(2));
                break;



            default:
                sendOutput = false;
                break;
        }
        System.out.println("GameController command processed");
        if (sendOutput) client.sendToServerVoid(0);
    }

    private boolean setFaction(Faction faction) {
        player.setFaction(faction);
        return true;
    }

    private boolean setDeck(Deck deck) {
        player.loadDeck(deck);
        return true;
    }

    private boolean setLeaders(Leader myLeader, Leader enemyLeader) {

        return true;
    }

    private boolean setHands(ArrayList<Card> myHand, ArrayList<Card> enemyHand) {
        gameMenu.loadHand(myHand, false);
        gameMenu.loadHand(enemyHand, true);
        return true;
    }
}
