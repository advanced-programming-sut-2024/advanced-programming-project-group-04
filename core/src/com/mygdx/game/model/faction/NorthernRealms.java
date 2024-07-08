package mygdx.game.model.faction;

import mygdx.game.model.card.AllCards;

import java.util.ArrayList;

import static mygdx.game.model.card.AllCards.*;

public class NorthernRealms extends Faction {

    private final static ArrayList<AllCards> cards = new ArrayList<>();

    static {
        cards.add(Ballista);
        cards.add(BlueStripesCommando);
        cards.add(Catapult);
        cards.add(DragonHunter);
        cards.add(Dethmold);
        cards.add(DunBannerMedic);
        cards.add(EsteradThyssen);
        cards.add(JohnNatalis);
        cards.add(KaedweniSiegeExpert);
        cards.add(KeiraMetz);
        cards.add(PhilippaEilhart);
        cards.add(PoorFuckingInfantry);
        cards.add(PrinceStennis);
        cards.add(RedanianFootSoldier);
        cards.add(SabrinaGlevissig);
        cards.add(SheldonSkaggs);
        cards.add(SiegeTower);
        cards.add(SiegfriedOfDenesle);
        cards.add(SigismundDijkstra);
        cards.add(SileDeTansarville);
        cards.add(Thaler);
        cards.add(Trebuchet);
        cards.add(VernonRoche);
        cards.add(Ves);
        cards.add(YarpenZigrin);
    }

    public NorthernRealms() {
        super("Northern Realms");
    }

    public static ArrayList<AllCards> getCards() {
        return cards;
    }
}
