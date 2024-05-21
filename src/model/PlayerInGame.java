package model;

import java.util.ArrayList;

import model.card.Card;
import model.card.specialcards.SpecialCard;
import model.card.unitcards.UnitCard;

public class PlayerInGame {

    private Player player;

    private int roundsWon;
    private int point;

    private ArrayList<UnitCard> melee = new ArrayList<>();
    private ArrayList<UnitCard> siege = new ArrayList<>();
    private ArrayList<UnitCard> range = new ArrayList<>();

    private SpecialCard meleeSpell;
    private SpecialCard siegeSpell;
    private SpecialCard rangeSpell;

    private ArrayList<Card> graveyard = new ArrayList<>();
    private ArrayList<Card> remainingCards = new ArrayList<>();
    
    
    public PlayerInGame (Player player){
        this.player = player;
        this.remainingCards = player.getDeck().getCards();
        this.roundsWon = 0;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getPoint() {
        // TODO az rooye cardha mohasebe shava
        return point;
    }

    public void incrementRoundsWon() {
        roundsWon++;
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

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public ArrayList<Card> getRemainingCards() {
        return remainingCards;
    }

    public void addToMelee(UnitCard unitCard) {
        melee.add(unitCard);
    }

    public void addToSiege(UnitCard unitCard) {
        siege.add(unitCard);
    }

    public void addToRange(UnitCard unitCard) {
        range.add(unitCard);
    }

    public void placeSpecialCardMelee(SpecialCard specialCard) {
        this.meleeSpell = specialCard;
    }

    public void placeSpecialCardSiege(SpecialCard specialCard) {
        this.siegeSpell = specialCard;
    }

    public void placeSpecialCardRange(SpecialCard specialCard) {
        this.rangeSpell = specialCard;
    }

}
