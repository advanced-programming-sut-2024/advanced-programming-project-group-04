package com.mygdx.game.model.faction;

import java.util.ArrayList;

import com.mygdx.game.model.card.AllCards;
import static com.mygdx.game.model.card.AllCards.*;

public class Monsters extends Faction{
    
    private static ArrayList<AllCards> monsterCards = new ArrayList<>();
    
    static {
        monsterCards.add(Draug);
        monsterCards.add(Imlerith);
        monsterCards.add(Leshen);
        monsterCards.add(Kayran);
        monsterCards.add(Toad);
        monsterCards.add(ArachasBehemoth);
        monsterCards.add(CroneBrewess);
        monsterCards.add(CroneWeavess);
        monsterCards.add(CroneWhispess);
        monsterCards.add(EarthElemental);
        monsterCards.add(Fiend);
        monsterCards.add(FireElemental);
        monsterCards.add(Forktail);
        monsterCards.add(Frightener);
        monsterCards.add(GraveHag);
        monsterCards.add(Griffin);
        monsterCards.add(IceGiant);
        monsterCards.add(PlagueMaiden);
        monsterCards.add(VampireKatakan);
        monsterCards.add(Werewolf);
        monsterCards.add(Arachas);
        monsterCards.add(Botchling);
        monsterCards.add(VampireBruxa);
        monsterCards.add(VampireEkimmara);
        monsterCards.add(VampireFleder);
        monsterCards.add(VampireGarkain);
        monsterCards.add(CelaenoHarpy);
        monsterCards.add(Cockatrice);
        monsterCards.add(Endrega);
        monsterCards.add(Foglet);
        monsterCards.add(Gargoyle);
        monsterCards.add(Harpy);
        monsterCards.add(Nekker);
        monsterCards.add(Wyvern);
        monsterCards.add(Ghoul);
    }

    public Monsters() {
        super("Monsters");
    }
}
