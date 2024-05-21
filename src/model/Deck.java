package model;

import java.util.ArrayList;

import model.card.Card;
import model.card.specialcards.SpecialCard;
import model.card.unitcards.UnitCard;
import model.leader.Leader;

public class Deck {
    private Leader leader;
    private ArrayList<Card> cards = new ArrayList<>();
    private final int UNIT_MIN = 22, SPELL_MAX = 10;
    
    public ArrayList<Card> getCards(){
        return cards;
    }
    
    public void setLeader(Leader leader){
        this.leader = leader;
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public boolean isCardInDeck(Card card){
        for(Card dummyCard : this.cards){
            if(card.equals(dummyCard)){
                return true;
            }
        }
        return false;
    }

    public void removeCard(Card card){
        if(isCardInDeck(card)){
            cards.remove(card);
        }
    }

    public boolean isNumberOfCardsValid(){
        return getNumberOfUnits() >= UNIT_MIN && getNumberOfSpecialCards() >= SPELL_MAX;
    }

    public int getNumberOfUnits() {
        int num = 0;
        for (Card card : this.cards) {
            if (card instanceof UnitCard) num++;
        }
        return num;
    }

    public int getNumberOfSpecialCards() {
        int num = 0;
        for (Card card : this.cards) {
            if (card instanceof SpecialCard) num++;
        }
        return num;
    }

    public Leader getLeader() {
        return this.leader;
    }
}
