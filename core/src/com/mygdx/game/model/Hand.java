package com.mygdx.game.model;

import com.mygdx.game.model.card.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() { return this.cards; }
}
