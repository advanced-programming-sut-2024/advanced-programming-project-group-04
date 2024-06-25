package com.mygdx.game.model.faction;

import java.util.ArrayList;

import static com.mygdx.game.model.card.AllCards.*;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.leader.Leader;

public class Faction {
    private final String name;

    private final static ArrayList<AllCards> neutralCards = new ArrayList<>();
    neutralCards = {

    };
    private ArrayList<Leader> leaders = new ArrayList<>();

    public Faction(String name) {
        this.name = name;
    }

    public String getImageURL() {
        String className = getClass().getSimpleName().toLowerCase();
        return "images/factions/" + className + ".png";
    }
}
