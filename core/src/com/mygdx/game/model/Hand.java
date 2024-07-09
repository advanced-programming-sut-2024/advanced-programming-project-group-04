package mygdx.game.model;

import mygdx.game.model.card.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() { return this.cards; }
}
