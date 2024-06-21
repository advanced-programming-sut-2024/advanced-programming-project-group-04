package model;

import java.util.ArrayList;

import model.card.Card;

public class PlayerInGame {

    private Player player;

    private int roundsWon;
    private int point;

    private boolean isPassed;

    private ArrayList<Card> melee = new ArrayList<>();
    private ArrayList<Card> siege = new ArrayList<>();
    private ArrayList<Card> range = new ArrayList<>();

    private Card meleeSpell;
    private Card siegeSpell;
    private Card rangeSpell;

    private ArrayList<Card> graveyard = new ArrayList<>();
    private ArrayList<Card> remainingCards = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();

    public PlayerInGame(Player player) {
        this.player = player;
        this.remainingCards = player.getDeck().getCards();
        this.roundsWon = 0;
        isPassed = false;
        drawInitialCards();
    }

    private void drawInitialCards() {
        for (int i = 0; i < 10; i++) {
            Card card = drawRandomCard();
            remainingCards.remove(card);
            hand.add(card);
        }
    }

    public void redrawCard(Card card) {
        Card newCard = drawRandomCard();
        remainingCards.add(card);
        hand.add(newCard);
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getPoint() {
        // TODO az rooye cardha mohasebe shavad
        return point;
    }

    public Card drawRandomCard() {
        int index = (int) (Math.random() * remainingCards.size());
        Card card = remainingCards.get(index);
        return card;
    }

    public void incrementRoundsWon() {
        roundsWon++;
    }

    public String removeUnitCard(Card unitCard) {
        for (Card card : melee) {
            if (card.equals(unitCard)) {
                melee.remove(card);
                return "aali";
            }
        }

        for (Card card : siege) {
            if (card.equals(unitCard)) {
                siege.remove(card);
                return "aali";
            }
        }

        for (Card card : range) {
            if (card.equals(unitCard)) {
                range.remove(card);
                return "aali";
            }
        }
        return "in card vojood nadasht dalghak";
    }



    public String removeSpellCard(Card specialCard) {
        if (siegeSpell.equals(specialCard)) {
            siegeSpell = null;
            return "aali";
        }

        if (rangeSpell.equals(specialCard)) {
            rangeSpell = null;
            return "aali";
        }

        if (meleeSpell.equals(specialCard)) {
            meleeSpell = null;
            return "aali";
        }
        return "in card vojood nadasht dalghak";
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Card> getMelee() {
        return melee;
    }

    public ArrayList<Card> getSiege() {
        return siege;
    }

    public ArrayList<Card> getRange() {
        return range;
    }

    public Card getMeleeSpell() {
        return meleeSpell;
    }

    public Card getSiegeSpell() {
        return siegeSpell;
    }

    public Card getRangeSpell() {
        return rangeSpell;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public ArrayList<Card> getRemainingCards() {
        return remainingCards;
    }

    public void addToMelee(Card unitCard) {
        melee.add(unitCard);
        hand.remove(unitCard);
    }

    public void addToSiege(Card unitCard) {
        siege.add(unitCard);
        hand.remove(unitCard);
    }

    public void addToRange(Card unitCard) {
        range.add(unitCard);
        hand.remove(unitCard);
    }

    public void placeSpecialCardMelee(Card specialCard) {
        this.meleeSpell = specialCard;
        hand.remove(specialCard);
    }

    public void placeSpecialCardSiege(Card specialCard) {
        this.siegeSpell = specialCard;
        hand.remove(specialCard);
    }

    public void placeSpecialCardRange(Card specialCard) {
        this.rangeSpell = specialCard;
        hand.remove(specialCard);
    }

    public int getRemainingCardsCount() {
        return remainingCards.size();
    }

    public int getGraveyardCount() {
        return graveyard.size();
    }

    public int getHandCount() {
        return hand.size();
    }

}
