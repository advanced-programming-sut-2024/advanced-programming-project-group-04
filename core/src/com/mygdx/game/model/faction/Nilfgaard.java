package mygdx.game.model.faction;

import mygdx.game.model.card.AllCards;

import java.util.ArrayList;

import static mygdx.game.model.card.AllCards.*;

public class Nilfgaard extends Faction {

    private final static ArrayList<AllCards> cards = new ArrayList<>();

    static {
        cards.add(ImperaBrigadeGuard);
        cards.add(StefanSkellen);
        cards.add(ShilardFitzOesterlen);
        cards.add(YoungEmissary);
        cards.add(CahirMawrDyffrynAepCeallach);
        cards.add(VattierDeRideaux);
        cards.add(MennoCoehoorn);
        cards.add(Puttkammer);
        cards.add(AssireVarAnahid);
        cards.add(BlackInfantryArcher);
        cards.add(TiborEggebracht);
        cards.add(RenualdAepMatsen);
        cards.add(FringillaVigo);
        cards.add(RottenMangonel);
        cards.add(HeavyZerrikanianFireScorpion);
        cards.add(ZerrikanianFireScorpion);
        cards.add(SiegeEngineer);
        cards.add(Albrich);
        cards.add(Cynthia);
        cards.add(EtolianAuxiliaryArchers);
        cards.add(LethoOfGulet);
        cards.add(MennoCoehoorn);
        cards.add(Morteisen);
        cards.add(MorvranVoorhis);
        cards.add(NausicaaCavalryRider);
        cards.add(Rainfarn);
        cards.add(SiegeTechnician);
        cards.add(Sweers);
        cards.add(Vanhemar);
        cards.add(Vreemde);
    }

    public Nilfgaard() {
        super("Nilfgaardian Empire");
    }

    public static ArrayList<AllCards> getCards() {
        return cards;
    }
}
