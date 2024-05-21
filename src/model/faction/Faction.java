package model.faction;

import java.util.ArrayList;

import model.card.Card;
import model.leader.Leader;

public class Faction {
    private final String name;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Leader> leaders = new ArrayList<>();


    public Faction(String name) {
        this.name = name;
    }
}
