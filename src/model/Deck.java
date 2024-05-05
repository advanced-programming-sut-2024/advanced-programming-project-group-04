package model;

import java.util.ArrayList;

import model.card.Card;
import model.leader.Leader;

public class Deck {
    private Leader leader;
    private ArrayList<Card> cards = new ArrayList<>();
    
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

    }

    public int getNumberOfUnits() {

    }

    public int getNumberOfSpellCards() {

    }

    public Leader getLeader() {
        return this.leader;
    }
}
