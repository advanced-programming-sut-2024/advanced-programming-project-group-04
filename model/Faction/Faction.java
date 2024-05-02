package model.Faction;

import java.util.ArrayList;
import model.*;
import model.Card.Card;

public class Faction {
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();

    public Faction(String name , ArrayList<Card> cards) {
        this.name = name;
        this.cards = cards;
    }
}
