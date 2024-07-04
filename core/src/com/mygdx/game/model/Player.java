package com.mygdx.game.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.mygdx.game.model.faction.Faction;
import com.mygdx.game.model.message.Message;


public class Player implements Serializable {
    private final int id;
    private String username;
    private String email;
    private String nickname;
    private int maxScore;
    private int gameCount;
    private int winCount;
    private int drawCount;
    private int lossCount;
    private String forgetPasswordQuestion;
    private String answerToQuestion;
    private HashMap<Player, ArrayList<Message>> sentMessages = new HashMap<>();
    private HashMap<Player, ArrayList<Message>> receivedMessages = new HashMap<>();
    private ArrayList<Player> friends = new ArrayList<>();

    private ArrayList<Player> incomingFriendRequests = new ArrayList<>();
    private ArrayList<Player> outgoingFriendRequests = new ArrayList<>();

    private Deck deck;

    private Faction selectedFaction;

    private ArrayList<Deck> savedDecks = new ArrayList<>();

    
    public Player (String username , String email , String nickname) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;

        Random random = new Random();
        this.id = Math.abs(random.nextInt());

        this.maxScore = 0;
        this.gameCount = 0;
        this.winCount = 0;
        this.drawCount = 0;
        this.lossCount = 0;

    }

    public int getId() { return this.id; }

    public String getUsername() { return this.username; }


    public boolean validateAnswerToQuestion(String answer) {
        return this.answerToQuestion.equals(answer);
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    // TODO calculate rank somhow

    public int getGameCount() {
        return this.gameCount;
    }

    public int getWinCount() {
        return this.winCount;
    }

    public int getDrawCount() {
        return this.drawCount;
    }

    public int getLossCount() {
        return this.lossCount;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Faction getSelectedFaction() {
        return this.selectedFaction;
    }

    public void setFaction(Faction faction) {
        this.selectedFaction = faction;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getForgetPasswordQuestion() {
        return forgetPasswordQuestion;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }

    public void selectFaction(Faction faction) {
        selectedFaction = faction;
    }

    public void saveDeck(Deck deck) {
        savedDecks.add(deck);
    }

    public void loadDeck(Deck deck) {
        this.deck = deck;
    }

    public void createNewDeck() { this.deck = new Deck(); }

    public boolean isDeckSaved(Deck deck) {
        for (Deck savedDeck : savedDecks) {
            if (savedDeck.equals(deck)) {
                return true;
            }
        }
        return false;
    }

    public boolean canStartGame() {
        if (selectedFaction == null) return false;
        else return deck.isValid();
    }

    public HashMap<Player, ArrayList<Message>> getSentMessages() {
        return sentMessages;
    }

    public HashMap<Player, ArrayList<Message>> getReceivedMessages() {
        return receivedMessages;
    }

    public void sendMessage(Player receiver, String content) {
        Message message = new Message(this, receiver, content);

        if (!this.sentMessages.containsKey(receiver)) {
            this.sentMessages.put(receiver, new ArrayList<>());
        }
        this.sentMessages.get(receiver).add(message);

        if (!receiver.receivedMessages.containsKey(this)) {
            receiver.receivedMessages.put(this, new ArrayList<>());
        }
        receiver.receivedMessages.get(this).add(message);
    }

    public ArrayList<Player> getFriends() {
        return friends;
    }

    public ArrayList<Player> getIncomingFriendRequests() {
        return incomingFriendRequests;
    }

    public ArrayList<Player> getOutgoingFriendRequests() {
        return outgoingFriendRequests;
    }

    public boolean sendFriendRequest(Player receiver) {
        if (outgoingFriendRequests.contains(receiver)) return false;
        this.outgoingFriendRequests.add(receiver);
        receiver.getIncomingFriendRequests().add(this);
        return true;
    }

    public boolean acceptFriendRequest(Player accepted) {
        if (incomingFriendRequests.contains(accepted)) {
            friends.add(accepted);
            accepted.friends.add(this);
            incomingFriendRequests.remove(accepted);
            accepted.getOutgoingFriendRequests().remove(this);
            return true;
        }
        return false;
    }

    public boolean rejectFriendRequest(Player rejected) {
        if (incomingFriendRequests.contains(rejected)) {
            incomingFriendRequests.remove(rejected);
            rejected.getOutgoingFriendRequests().remove(this);
            return true;
        }
        return false;
    }

    public ArrayList<Message> getChatWithPlayer(Player player) {
        ArrayList<Message> combinedMessages = new ArrayList<>();

        if (this.sentMessages.containsKey(player)) {
            combinedMessages.addAll(this.sentMessages.get(player));
        }

        if (this.receivedMessages.containsKey(player)) {
            combinedMessages.addAll(this.receivedMessages.get(player));
        }

        Collections.sort(combinedMessages, Comparator.comparingLong(Message::getSendTime));

        return combinedMessages;
    }
}