package model.faction;

import java.util.ArrayList;

import model.card.Card;

public class Faction {
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();

    public Faction(String name , ArrayList<Card> cards) {
        this.name = name;
        this.cards = cards;
    }
}
