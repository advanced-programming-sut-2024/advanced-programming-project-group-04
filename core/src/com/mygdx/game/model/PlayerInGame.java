package com.mygdx.game.model;

import java.util.ArrayList;

import com.mygdx.game.model.card.Card;

public class PlayerInGame {

    private Player player;

    private int roundsWon;
    private int point;

    private boolean isPassed;

    // TODO
    private boolean isLeaderUsed;

    private ArrayList<Card> melee = new ArrayList<>();
    private ArrayList<Card> siege = new ArrayList<>();
    private ArrayList<Card> range = new ArrayList<>();

    private Card meleeSpell;
    private Card siegeSpell;
    private Card rangeSpell;

    private ArrayList<Card> graveyard = new ArrayList<>();
    private ArrayList<Card> deckInGame = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();

    public PlayerInGame(Player player) {
        this.player = player;
        this.deckInGame = player.getDeck().getCards();
        this.roundsWon = 0;
        isPassed = false;
        drawInitialCards();
    }

    private void drawInitialCards() {
        for (int i = 0; i < 10; i++) {
            Card card = drawRandomCardFromDeckInGame();
            deckInGame.remove(card);
            hand.add(card);
        }
    }
    public void redrawCard(Card card) {
        Card newCard = drawRandomCardFromDeckInGame();
        deckInGame.add(card);
        hand.remove(card);
        hand.add(newCard);
    }

    public int getRoundsWon() {
        return roundsWon;
    }
    public void setRoundsWon(int number) {
        roundsWon = number;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int number) {
        point = number;
    }

    public int calculatePoint() {
        // TODO
        return 0;
    }

    public Card drawRandomCardFromDeckInGame() {
        int index = (int) (Math.random() * deckInGame.size());
        Card card = deckInGame.get(index);
        return card;
    }
    public Card drawRandomCardFromGraveyard() {
        int index = (int) (Math.random() * graveyard.size());
        Card card = graveyard.get(index);
        return card;
    }

    public void incrementRoundsWon() {
        roundsWon++;
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
    public void setIsPassed(boolean bool) {
        isPassed = bool;
    }
    
    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }
    public ArrayList<Card> getDeckInGame() {
        return deckInGame;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addToGraveyard(Card card) {
        graveyard.add(card);
    }
    public void removeFromGraveyard(Card card) {
        graveyard.remove(card);
    }
    public void addToHand(Card card) {
        hand.add(card);
    }
    public void removeFromHand(Card card) {
        hand.remove(card);
    }
    public void addToDeckInGame(Card card) {
        deckInGame.add(card);
    }
    public void removeFromDeckInGame(Card card) {
        deckInGame.remove(card);
    }



    public void addToMelee(Card unitCard) {
        melee.add(unitCard);
    }
    public void addToSiege(Card unitCard) {
        siege.add(unitCard);
    }
    public void addToRange(Card unitCard) {
        range.add(unitCard);
    }
    public void placeSpellMelee(Card specialCard) {
        this.meleeSpell = specialCard;
    }
    public void placeSpellSiege(Card specialCard) {
        this.siegeSpell = specialCard;
    }
    public void placeSpellRange(Card specialCard) {
        this.rangeSpell = specialCard;
    }
    public void removeFromMelee(Card card) {
        melee.remove(card);
    }
    public void removeFromRange(Card card) {
        range.remove(card);
    }
    public void removeFromSiege(Card card) {
        siege.remove(card);
    }
    public void removeSpellMelee(Card card) {
        meleeSpell = null;
    }
    public void removeSpellRange(Card card) {
        rangeSpell = null;
    }
    public void removeSpellSiege(Card card) {
        siegeSpell = null;
    }

    public ArrayList<Card> getCardsWithSameNameFromHand (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : hand) {
            if(card.getName().equals(sampleCard.getName())) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromDeckInGame (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : deckInGame) {
            if(card.getName().equals(sampleCard.getName())) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromGraveyard (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : graveyard) {
            if(card.getName().equals(sampleCard.getName())) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromMelee (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : melee) {
            if(card.getName().equals(sampleCard.getName())) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromRange (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : range) {
            if(card.getName().equals(sampleCard.getName())) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromSiege (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : siege) {
            if(card.getName().equals(sampleCard.getName())) {
                sample.add(card);
            }
        }
        return sample;
    }




    public void meleeCurrentHpTimesInt (int number) {
        for (Card card : melee) {
            card.setCurrentHP(card.getCurrentHP() * number);
        }
    }
    public void rangeCurrentHpTimesInt (int number) {
        for (Card card : range) {
            card.setCurrentHP(card.getCurrentHP() * number);
        }
    }
    public void siegeCurrentHpTimesInt (int number) {
        for (Card card : siege) {
            card.setCurrentHP(card.getCurrentHP() * number);
        }
    }

    public int getDeckInGameCount() {
        return deckInGame.size();
    }
    public int getGraveyardCount() {
        return graveyard.size();
    }
    public int getHandCount() {
        return hand.size();
    }

    
}
