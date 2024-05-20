package model.faction;

import java.util.ArrayList;

import model.card.Card;

public class Faction {
    private final String name;
    protected final ArrayList<Card> cards = new ArrayList<>();

    public Faction(String name) {
        this.name = name;
    }
}
