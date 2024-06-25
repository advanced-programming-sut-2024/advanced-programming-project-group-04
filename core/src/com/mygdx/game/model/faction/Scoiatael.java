package com.mygdx.game.model.faction;

import java.util.ArrayList;

import com.mygdx.game.model.card.AllCards;
import static com.mygdx.game.model.card.AllCards.*;

public class Scoiatael extends Faction{

    private static ArrayList<AllCards> scoiataelCards = new ArrayList<>();

    static {
        scoiataelCards.add(ElvenSkirmisher);
        scoiataelCards.add(Iorveth);
        scoiataelCards.add(Yaevinn);
        scoiataelCards.add(CiaranAepEasnillien);
        scoiataelCards.add(DennisCranmer);
        scoiataelCards.add(DolBlathannaScout);
        scoiataelCards.add(DolBlathannaArcher);
        scoiataelCards.add(DwarvenSkirmisher);
        scoiataelCards.add(Filavandrel);
        scoiataelCards.add(HavekarHealer);
        scoiataelCards.add(HavekarSmuggler);
        scoiataelCards.add(IdaEmeanAep);
        scoiataelCards.add(Riordain);
        scoiataelCards.add(Toruviel);
        scoiataelCards.add(VriheddBrigadeRecruit);
        scoiataelCards.add(MahakamanDefender);
        scoiataelCards.add(VriheddBrigadeVeteran);
        scoiataelCards.add(Milva);
        scoiataelCards.add(Seasenthessis);
        scoiataelCards.add(Schirru);
        scoiataelCards.add(BarclayEls);
        scoiataelCards.add(Eithne);
        scoiataelCards.add(IsengrimFaoiltiarna);
    }
    public Scoiatael() {
        super("Scoia’tael");
    }
}
