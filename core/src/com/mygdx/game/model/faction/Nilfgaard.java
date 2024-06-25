package com.mygdx.game.model.faction;

import static com.mygdx.game.model.card.AllCards.*;

import java.util.ArrayList;

import com.mygdx.game.model.card.AllCards;

public class Nilfgaard extends Faction{

    private static ArrayList<AllCards> nilfgaardCards = new ArrayList<>();

    static {
        nilfgaardCards.add(ImperaBrigadeGuard);
        nilfgaardCards.add(StefanSkellen);
        nilfgaardCards.add(ShilardFitzOesterlen);
        nilfgaardCards.add(YoungEmissary);
        nilfgaardCards.add(CahirMawrDyffrynAepCeallach);
        nilfgaardCards.add(VattierDeRideaux);
        nilfgaardCards.add(MennoCoehorn);
        nilfgaardCards.add(Puttkammer);
        nilfgaardCards.add(AssireVarAnahid);
        nilfgaardCards.add(BlackInfantryArcher);
        nilfgaardCards.add(TiborEggebracht);
        nilfgaardCards.add(RenualdAepMatsen);
        nilfgaardCards.add(FringillaVigo);
        nilfgaardCards.add(RottenMangonel);
        nilfgaardCards.add(HeavyZerrikanianFireScorpion);
        nilfgaardCards.add(ZerrikanianFireScorpion);
        nilfgaardCards.add(SiegeEngineer);
        nilfgaardCards.add(Albrich);
        nilfgaardCards.add(Cynthia);
        nilfgaardCards.add(EtolianAuxiliaryArchers);
        nilfgaardCards.add(LethoOfGulet);
        nilfgaardCards.add(MennoCoehoorn);
        nilfgaardCards.add(Morteisen);
        nilfgaardCards.add(MorvranVoorhis);
        nilfgaardCards.add(NausicaaCavalryRider);
        nilfgaardCards.add(Rainfarn);
        nilfgaardCards.add(SiegeTechnician);
        nilfgaardCards.add(Sweers);
        nilfgaardCards.add(Vanhemar);
        nilfgaardCards.add(Vreemde);
    }

    public Nilfgaard() {
        super("Nilfgaardian Empire");
    }
}
