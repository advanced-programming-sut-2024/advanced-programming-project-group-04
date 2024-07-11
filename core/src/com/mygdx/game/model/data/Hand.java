package mygdx.game.model.data;

import mygdx.game.model.card.Card;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {
    private ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() { return this.cards; }
}
