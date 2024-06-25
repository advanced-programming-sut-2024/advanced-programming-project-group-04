package com.mygdx.game.model.faction;

import java.util.ArrayList;

import static com.mygdx.game.model.card.AllCards.*;

import com.mygdx.game.model.card.AllCards;

public class NorthernRealms extends Faction{
    
    private static ArrayList<AllCards> northenRealmsCards = new ArrayList<>();

    static {
        northenRealmsCards.add(Ballista);
        northenRealmsCards.add(BlueStripesCommando);
        northenRealmsCards.add(Catapult);
        northenRealmsCards.add(DragonHunter);
        northenRealmsCards.add(Dethmold);
        northenRealmsCards.add(DunBannerMedic);
        northenRealmsCards.add(EsteradThyssen);
        northenRealmsCards.add(JohnNatalis);
        northenRealmsCards.add(KaedweniSiegeExpert);
        northenRealmsCards.add(KeiraMetz);
        northenRealmsCards.add(PhilippaEilhart);
        northenRealmsCards.add(PoorFuckingInfantry);
        northenRealmsCards.add(PrinceStennis);
        northenRealmsCards.add(RedanianFootSoldier);
        northenRealmsCards.add(SabrinaGlevissing);
        northenRealmsCards.add(SheldonSkaggs);
        northenRealmsCards.add(SiegeTower);
        northenRealmsCards.add(SiegfriedOfDenesle);
        northenRealmsCards.add(SigismundDijkstra);
        northenRealmsCards.add(SleDeTansarville);
        northenRealmsCards.add(Thaler);
        northenRealmsCards.add(Trebuchet);
        northenRealmsCards.add(VernonRoche);
        northenRealmsCards.add(Ves);
        northenRealmsCards.add(YarpenZirgrin);
    }
    
    public NorthernRealms() {
        super("Northern Realms");
    }
}
