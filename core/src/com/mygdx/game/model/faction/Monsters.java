package  mygdx.game.model.faction;

import java.util.ArrayList;

import  mygdx.game.model.card.AllCards;
import static  mygdx.game.model.card.AllCards.*;

public class Monsters extends Faction{
    
    private final static ArrayList<AllCards> cards = new ArrayList<>();
    
    static {
        cards.add(Draug);
        cards.add(Imlerith);
        cards.add(Leshen);
        cards.add(Kayran);
        cards.add(Toad);
        cards.add(ArachasBehemoth);
        cards.add(CroneBrewess);
        cards.add(CroneWeavess);
        cards.add(CroneWhispess);
        cards.add(EarthElemental);
        cards.add(Fiend);
        cards.add(FireElemental);
        cards.add(Forktail);
        cards.add(Frightener);
        cards.add(GraveHag);
        cards.add(Griffin);
        cards.add(IceGiant);
        cards.add(PlagueMaiden);
        cards.add(VampireKatakan);
        cards.add(Werewolf);
        cards.add(Arachas);
        cards.add(Botchling);
        cards.add(VampireBruxa);
        cards.add(VampireEkimmara);
        cards.add(VampireFleder);
        cards.add(VampireGarkain);
        cards.add(CelaenoHarpy);
        cards.add(Cockatrice);
        cards.add(Endrega);
        cards.add(Foglet);
        cards.add(Gargoyle);
        cards.add(Harpy);
        cards.add(Nekker);
        cards.add(Wyvern);
        cards.add(Ghoul);
    }

    public Monsters() {
        super("Monsters");
    }

    public static ArrayList<AllCards> getCards() {
        return cards;
    }
}
