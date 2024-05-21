package model;

import java.util.ArrayList;

import model.card.Card;
import model.card.specialcards.SpecialCard;
import model.card.unitcards.UnitCard;

public class PlayerInGame {

    private Player player;

    private int roundsWon;
    private int point;

    private boolean isPassed;

    private ArrayList<UnitCard> melee = new ArrayList<>();
    private ArrayList<UnitCard> siege = new ArrayList<>();
    private ArrayList<UnitCard> range = new ArrayList<>();

    private SpecialCard meleeSpell;
    private SpecialCard siegeSpell;
    private SpecialCard rangeSpell;

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

    public String removeUnitCard(UnitCard unitCard) {
        for (UnitCard card : melee) {
            if (card.equals(unitCard)) {
                melee.remove(card);
                return "aali";
            }
        }

        for (UnitCard card : siege) {
            if (card.equals(unitCard)) {
                siege.remove(card);
                return "aali";
            }
        }

        for (UnitCard card : range) {
            if (card.equals(unitCard)) {
                range.remove(card);
                return "aali";
            }
        }
        return "in card vojood nadasht dalghak";
    }



    public String removeSpecialCard(SpecialCard card) {
        if (siegeSpell.equals(card)) {
            siegeSpell = null;
            return "aali";
        }

        if (rangeSpell.equals(card)) {
            rangeSpell = null;
            return "aali";
        }

        if (meleeSpell.equals(card)) {
            meleeSpell = null;
            return "aali";
        }
        return "in card vojood nadasht dalghak";
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<UnitCard> getMelee() {
        return melee;
    }

    public ArrayList<UnitCard> getSiege() {
        return siege;
    }

    public ArrayList<UnitCard> getRange() {
        return range;
    }

    public SpecialCard getMeleeSpell() {
        return meleeSpell;
    }

    public SpecialCard getSiegeSpell() {
        return siegeSpell;
    }

    public SpecialCard getRangeSpell() {
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

    public void addToMelee(UnitCard unitCard) {
        melee.add(unitCard);
        hand.remove(unitCard);
    }

    public void addToSiege(UnitCard unitCard) {
        siege.add(unitCard);
        hand.remove(unitCard);
    }

    public void addToRange(UnitCard unitCard) {
        range.add(unitCard);
        hand.remove(unitCard);
    }

    public void placeSpecialCardMelee(SpecialCard specialCard) {
        this.meleeSpell = specialCard;
        hand.remove(specialCard);
    }

    public void placeSpecialCardSiege(SpecialCard specialCard) {
        this.siegeSpell = specialCard;
        hand.remove(specialCard);
    }

    public void placeSpecialCardRange(SpecialCard specialCard) {
        this.rangeSpell = specialCard;
        hand.remove(specialCard);
    }

    public int getRemainingCardsCount() {
        return remainingCards.size();
    }

    public int getGraveyardCount() {
        return graveyard.size();
    }

    public int getHnadCount() {
        return hand.size();
    }

}
