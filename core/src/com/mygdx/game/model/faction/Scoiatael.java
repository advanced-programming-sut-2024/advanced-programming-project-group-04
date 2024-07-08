package mygdx.game.model.faction;

import mygdx.game.model.card.AllCards;

import java.util.ArrayList;

import static mygdx.game.model.card.AllCards.*;

public class Scoiatael extends Faction {

    private final static ArrayList<AllCards> cards = new ArrayList<>();

    static {
        cards.add(ElvenSkirmisher);
        cards.add(Iorveth);
        cards.add(Yaevinn);
        cards.add(CiaranAepEasnillien);
        cards.add(DennisCranmer);
        cards.add(DolBlathannaScout);
        cards.add(DolBlathannaArcher);
        cards.add(DwarvenSkirmisher);
        cards.add(FilavandrelAenFidhail);
        cards.add(HavekarHealer);
        cards.add(HavekarSmuggler);
        cards.add(IdaEmeanAepSivney);
        cards.add(Riordain);
        cards.add(Toruviel);
        cards.add(VriheddBrigadeRecruit);
        cards.add(MahakamanDefender);
        cards.add(VriheddBrigadeVeteran);
        cards.add(Milva);
        cards.add(Saesenthessis);
        cards.add(Schirru);
        cards.add(BarclayEls);
        cards.add(Eithne);
        cards.add(IsengrimFaolitarna);
    }

    public Scoiatael() {
        super("Scoia’tael");
    }

    public static ArrayList<AllCards> getCards() {
        return cards;
    }
}
