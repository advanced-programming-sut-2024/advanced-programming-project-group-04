package com.mygdx.game.model;


import java.util.ArrayList;

import com.mygdx.game.model.faction.Faction;


public class Player {
    private static ArrayList<Player> allPlayers = new ArrayList<>();

    private String username;
    private String password;
    private String email;
    private String nickname;
    private int maxScore;
    private int gameCount;
    private int winCount;
    private int drawCount;
    private int lossCount;
    private String forgetPasswordQuestion;
    private String awnserToQuestion;

    private Deck deck;
    private Faction selectedFaction;

    private ArrayList<Deck> savedDecks = new ArrayList<>();
    
    public Player (String username , String password , String email , String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;

        this.maxScore = 0;
        this.gameCount = 0;
        this.winCount = 0;
        this.drawCount = 0;
        this.lossCount = 0;
    }

    public static Player findPlayerByUsername (String username) {
        for (Player player : allPlayers) {
            if (player.username.equals(username)) {
                return player;
            }
        }
        return null;
    }
    
    public boolean validatePassword (String password) {
        return this.password.equals(password);
    }

    public boolean validateAwnserToQuestion (String answer) {
        return this.awnserToQuestion.equals(answer);
    }

    public String getNickname () {
        return this.nickname;
    }

    public String getEmail () {
        return this.email;
    }

    public int getMaxScore () {
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

    public Faction geFaction() {
        return this.selectedFaction;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isDeckSaved(Deck deck) {
        for (Deck savedDeck : savedDecks) {
            if (savedDeck.equals(deck)) {
                return true;
            }
        }
        return false;
    }
}