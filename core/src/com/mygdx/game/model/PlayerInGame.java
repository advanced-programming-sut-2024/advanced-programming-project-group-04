package com.mygdx.game.model;

import java.util.ArrayList;

import com.mygdx.game.model.card.Card;

public class PlayerInGame {

    private Player player;

    private int remainingLives;
    private int point;

    private boolean isPassed;

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
        this.remainingLives = 2;
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
        deckInGame.remove(newCard);
    }

    public int getRemainingLives() {
        return remainingLives;
    }
    public void setRemainingLives(int number) {
        remainingLives = number;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int number) {
        point = number;
    }

    public Card drawRandomCardFromDeckInGame() {
        int index = (int) (Math.random() * deckInGame.size());
        Card card = deckInGame.get(index);
        return card;
    }

    public Card addRandomCardToHandFromDeck() {
        Card card = drawRandomCardFromDeckInGame();
        hand.add(card);
        deckInGame.remove(card);
        return card;
    }

    public void addRandomCardToHandFromGrave() {
        Card card = drawRandomCardFromGraveyard();
        hand.add(card);
        graveyard.remove(card);
    }

    public Card drawRandomCardFromGraveyard() {
        int index = (int) (Math.random() * graveyard.size());
        Card card = graveyard.get(index);
        return card;
    }

    public void decreaseRemainingLives() {
        remainingLives--;
    }

 
    public boolean isPassed() {
        return isPassed;
    }
    public void setIsPassed(boolean bool) {
        isPassed = bool;
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



    public void removeCard (Card card, Position position) {
        if (position.equals(Position.Melee)) {
            removeFromMelee(card);
        } else if (position.equals(Position.Range)) {
            removeFromRange(card);
        } else if (position.equals(Position.Siege)) {
            removeFromSiege(card);
        } else if (position.equals(Position.SpellMelee)) {
            removeSpellMelee(card);
        } else if (position.equals(Position.SpellRange)) {
            removeSpellRange(card);
        } else if (position.equals(Position.SpellSiege)) {
            removeSpellSiege(card);
        } else {
            
        }
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

    // Muster related functions
    public ArrayList<Card> getCardsWithSameNameFromHand (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : hand) {
            if(card.musterEquality(sampleCard)) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromDeckInGame (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : deckInGame) {
            if(card.musterEquality(sampleCard)) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromGraveyard (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : graveyard) {
            if(card.musterEquality(sampleCard)) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromMelee (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : melee) {
            if(card.musterEquality(sampleCard)) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromRange (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : range) {
            if(card.musterEquality(sampleCard)) {
                sample.add(card);
            }
        }
        return sample;
    }
    public ArrayList<Card> getCardsWithSameNameFromSiege (Card card) {
        ArrayList<Card> sample = new ArrayList<>();
        for (Card sampleCard : siege) {
            if(card.musterEquality(sampleCard)) {
                sample.add(card);
            }
        }
        return sample;
    }



    // Commander's horn and Tight bond related functions
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
    public void someCardsCurrentHpTimesInt (int number , ArrayList<Card> someCards) {
        for (Card card : someCards) {
            card.setCurrentHP(card.getCurrentHP()*number);
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

    public ArrayList<Card> getCardRowFromPosition(Position position) {
        // range -> range , spellMelee -> melee
        if (position.equals(Position.Melee)) {
            return getMelee();
        } else if (position.equals(Position.Range)) {
            return getRange();
        } else if (position.equals(Position.Siege)) {
            return getSiege();
        } else if (position.equals(Position.SpellMelee)) {
            return getMelee();
        } else if (position.equals(Position.SpellRange)) {
            return getRange();
        } else if (position.equals(Position.SpellSiege)) {
            return getSiege();
        } else {
            return null;
        }
    }

    public Position findCardInGame(Card card) {
        for (Card sampleCard : getMelee()) {
            if (card.equals(sampleCard)) {
                return Position.Melee;
            }
        }
        for (Card sampleCard : getRange()) {
            if (card.equals(sampleCard)) {
                return Position.Range;
            }
        }
        for (Card sampleCard : getSiege()) {
            if (card.equals(sampleCard)) {
                return Position.Siege;
            }
        }
        if (card.equals(getMeleeSpell())) {
            return Position.SpellMelee;
        }
        if (card.equals(getRangeSpell())) {
            return Position.SpellRange;
        }
        if (card.equals(getSiegeSpell())) {
            return Position.SpellSiege;
        } else {
            return null;
        }
    }

    public void setMeleeCardsIsWeather (boolean bool) {
        for (Card card : melee) {
            card.setIsWeathered(bool);
        }
    }
    public void setRangeCardsIsWeather (boolean bool) {
        for (Card card : range) {
            card.setIsWeathered(bool);
        }
    }
    public void setSiegeCardsIsWeather (boolean bool) {
        for (Card card : siege) {
            card.setIsWeathered(bool);
        }
    }
    
    public boolean getIsLeaderUsed() {
        return isLeaderUsed;
    }
    public void setIsLeaderUsed(boolean bool) {
        isLeaderUsed = bool;
    }


    public ArrayList<Card> getAllCards() {
        ArrayList<Card> allOfTheCards = new ArrayList<>();
        allOfTheCards.addAll(melee);
        allOfTheCards.addAll(range);
        allOfTheCards.addAll(siege);
        if (meleeSpell != null) allOfTheCards.add(meleeSpell);

        if (rangeSpell != null) allOfTheCards.add(rangeSpell);
        if (siegeSpell != null) allOfTheCards.add(siegeSpell);
        return allOfTheCards;
    }

    public int getTotalHP() {
        int sum = 0;
        for (Card card : getAllCards()) {
            sum += card.getCurrentHP();
        }
        return sum;
    }


}
