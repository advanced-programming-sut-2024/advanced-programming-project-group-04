package com.mygdx.game.model.faction;

import java.util.ArrayList;

import com.mygdx.game.model.card.AllCards;
import static com.mygdx.game.model.card.AllCards.*;

import com.mygdx.game.model.leader.Leader;

public class Faction {
    private final String name;

    private final static ArrayList<AllCards> neutralCards = new ArrayList<>();

    private ArrayList<Leader> leaders = new ArrayList<>();

    static {
        neutralCards.add(BitingFrost);
        neutralCards.add(ImpenetrableFog);
        neutralCards.add(TorrentialRain);
        neutralCards.add(SkelligeStorm);
        neutralCards.add(ClearWeather);
        neutralCards.add(Scorch);
        neutralCards.add(CommandersHorn);
        neutralCards.add(Decoy);
        neutralCards.add(Dandelion);
        neutralCards.add(Cow);
        neutralCards.add(EmielRegis);
        neutralCards.add(GaunterODimm);
        neutralCards.add(GaunterODimmDarkness);
        neutralCards.add(GeraltOfRivia);
        neutralCards.add(Avallach);
        neutralCards.add(OlgierdVonEverec);
        neutralCards.add(TrissMerigold);
        neutralCards.add(Vesemir);
        neutralCards.add(Villentretenmerth);
        neutralCards.add(YenneferOfVengerberg);
        neutralCards.add(ZoltanChivay);
        neutralCards.add(BovineDefenseForce);
        neutralCards.add(CirillaFionaElenRiannon);
    }

    public Faction(String name) {
        this.name = name;
        
    }

    public String getImageURL() {
        String className = getClass().getSimpleName().toLowerCase();
        return "images/factions/" + className + ".png";
    }

    public static ArrayList<AllCards> getNeutralCards() {
        return neutralCards;
    }
}
