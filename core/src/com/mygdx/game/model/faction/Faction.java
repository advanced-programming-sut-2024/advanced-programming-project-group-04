package mygdx.game.model.faction;

import mygdx.game.model.card.AllCards;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static mygdx.game.model.card.AllCards.*;

public abstract class Faction implements Serializable {
    private final String name;

    private final static ArrayList<AllCards> neutralCards = new ArrayList<>();

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

    public String getAssetName() {
        return getClass().getSimpleName();
    }

    public String getImageURL() {
        return "images/factions/" + getAssetName() + ".png";
    }

    public String getName() {
        return name;
    }

    public static ArrayList<AllCards> getNeutralCards() {
        return neutralCards;
    }

    public static ArrayList<AllCards> getCardsFromFaction(Faction faction) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException,  java.lang.reflect.InvocationTargetException {
        return (ArrayList<AllCards>) faction.getClass().getMethod("getCards").invoke(null);
    }
}
