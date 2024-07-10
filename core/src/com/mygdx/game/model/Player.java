package mygdx.game.model;


import mygdx.game.model.faction.Faction;
import mygdx.game.model.message.Message;

import java.io.Serializable;
import java.util.*;


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
    private boolean twoFAEnabled;
    private Rank rank;
    private int LP;

    private ArrayList<Player> incomingFriendRequests = new ArrayList<>();
    private ArrayList<Player> outgoingFriendRequests = new ArrayList<>();

    private Deck deck;

    private Faction selectedFaction;

    private ArrayList<Deck> savedDecks = new ArrayList<>();


    public Player(String username, String email, String nickname) {
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
        twoFAEnabled = false;
        LP = 0;

    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean validateAnswerToQuestion(String answer) {
        if (answerToQuestion == null) return false;
        return this.answerToQuestion.equals(answer);
    }

    public void setAnswerToQuestion(String answer) {
        answerToQuestion = answer;
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

    public void setForgetPasswordQuestion(String question) {
        this.forgetPasswordQuestion = question;
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

    public void createNewDeck() {
        this.deck = new Deck();
    }

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

    public boolean getTwoFAEnabled() {
        return twoFAEnabled;
    }

    public void toggleTwoFA() {
        twoFAEnabled = !twoFAEnabled;
    }

    public int getLP() {
        return LP;
    }

    public void addLP(int amount) {
        LP += amount;
        if (LP < 0) LP = 0;
    }

    public Rank getRank() {
        for (Rank rank : Rank.values()) {
            if (LP >= rank.getMinimumLP()) {
                return rank;
            }
        }
        return null; // should never reach this
    }

    public int calculateLPGains(Player opponent, GameResult result) {
        int opponentLP = opponent.getLP();
        int lpDifference = opponentLP - this.LP;
        int lpChange = 0;

        switch (result) {
            case Win:
                lpChange = 25 + lpDifference / 20;
                lpChange = Math.max(lpChange, 1);
                break;

            case Loss:
                lpChange = -20 + lpDifference / 20;
                lpChange = Math.min(lpChange, -1);
                break;

            case Draw:
                lpChange = 2 + lpDifference / 40;
                break;
        }

        return lpChange;
    }
}