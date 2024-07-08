package  mygdx.game.model.faction;

import java.util.ArrayList;

import  mygdx.game.model.card.AllCards;
import static  mygdx.game.model.card.AllCards.*;


public class Skellige extends Faction{

    private final static ArrayList<AllCards> cards = new ArrayList<>();

    static {
        cards.add(Mardroeme);
        cards.add(Berserker);
        cards.add(TransformedVidkaarl);
        cards.add(Svanrige);
        cards.add(Udalryk);
        cards.add(DonarAnHindar);
        cards.add(ClanAnCraite);
        cards.add(BlueboyLugos);
        cards.add(MadmanLugos);
        cards.add(Cerys);
        cards.add(Kambi);
        cards.add(BirnaBran);
        cards.add(ClanDrummondShieldMaiden);
        cards.add(ClanTordarrochArmorsmith);
        cards.add(ClanDimunPirate);
        cards.add(ClanBrokvarArcher);
        cards.add(Ermion);
        cards.add(Hjalmar);
        cards.add(YoungBerserker);
        cards.add(TransformedYoungVidkaarl);
        cards.add(LightLongship);
        cards.add(HolgerBlackhand);
        cards.add(WarLongship);
        cards.add(DraigBonDhu);
        cards.add(Olaf);
        cards.add(Hemdall);
        cards.add(ClanHeymaeySkald);
    }

    public Skellige() {
        super("Skellige");
    }

    public static ArrayList<AllCards> getCards() {
        return cards;
    }
}
