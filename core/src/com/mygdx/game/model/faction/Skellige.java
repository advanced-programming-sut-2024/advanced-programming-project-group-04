package com.mygdx.game.model.faction;

import java.util.ArrayList;

import com.mygdx.game.model.card.AllCards;
import static com.mygdx.game.model.card.AllCards.*;


public class Skellige extends Faction{

    private static ArrayList<AllCards> skelligeCards = new ArrayList<>();

    static {
        skelligeCards.add(Mardoeme);
        skelligeCards.add(Berserker);
        skelligeCards.add(TransformedVidkaarl);
        skelligeCards.add(Svanrige);
        skelligeCards.add(Udalryk);
        skelligeCards.add(DonarAnHindar);
        skelligeCards.add(ClanAnCraite);
        skelligeCards.add(BlueboyLugos);
        skelligeCards.add(MadmanLugos);
        skelligeCards.add(Cerys);
        skelligeCards.add(Kambi);
        skelligeCards.add(BirnaBran);
        skelligeCards.add(ClanDrummondShieldmaiden);
        skelligeCards.add(ClanTordarrochArmorsmith);
        skelligeCards.add(ClanDimunPirate);
        skelligeCards.add(ClanBrokvarArcher);
        skelligeCards.add(Ermion);
        skelligeCards.add(Hjalmar);
        skelligeCards.add(YoungBerserker);
        skelligeCards.add(TransformedYoungVidkaarl);
        skelligeCards.add(LightLongship);
        skelligeCards.add(HolgerBlackhand);
        skelligeCards.add(WarLongship);
        skelligeCards.add(DraigBonDhu);
        skelligeCards.add(Olaf);
        skelligeCards.add(Hemdall);
        skelligeCards.add(ClanHeymaeySkald);    
    }

    public Skellige() {
        super("Skellige");
    }
}
