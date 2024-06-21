package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.*;

import static com.mygdx.game.model.card.Position.*;

public enum AllCards {
    Mardoeme("Mardoeme", 0, 3, SpecialDuper, new Mardroeme(),
            "This Spell card has Mardoeme Ability and can be placed in the commmander's Horn spot . it effects the row that it was placed in"),
    Berserker("Berserker", 4, 1, CloseCombat, new Berserker(),
            "Transforms into Vidkaarl when Mardroeme is used.Vidkaarl has Morale Boost effect."),
    Vidkaarl("Vidkaarl", 14, 0, CloseCombat, new MoralBoost(),
            "it can only be played when a berserker transform into it."),
    Svanrige("Svanrige", 4, 1, CloseCombat, new NoAbility(), ""),
    Udalryk("Udalryk", 4, 1, CloseCombat, new NoAbility(), ""),
    DonarAnHindar("Donar an Hindar", 4, 1, CloseCombat, new NoAbility(), ""),
    ClanAnCraite("Clan An Craite", 6, 3, CloseCombat, new TightBond(), ""),
    MadmanLugos("Madman Lugos", 6, 1, CloseCombat, new NoAbility(), ""),
    Cerys("Cerys", 10, 1, CloseCombat, new Muster(),
            "Its Muster effect will summon Shield Maiden cards and other musters."),
    Kambi("Kambi", 11, 1, CloseCombat, new Transformers(),
            "Turns into a card with a power of 8 after one round"),
    BirnaBran("Birna Bran", 2, 1, CloseCombat, new Medic(), ""),
    ClanDrummondShieldmaiden("Clan Drummond Shieldmaiden", 4, 3, CloseCombat, new TightBond(),
            "Can be Mustered by Cerys."),
    ClanDimunPirate("Clan Dimun Pirate", 6, 1, RangedCombat, new Scorch(),
            "kills opponent card(s) with most power(does not matter in which row in this card)"),
    ClanBrokvarArcher("Clan Brokvar Archer", 6, 3, RangedCombat, new NoAbility(), ""),
    Ermion("Ermion", 8, 1, RangedCombat, new Mardroeme(), ""),
    Hjalmar("Hjalmar", 10, 1, RangedCombat, new NoAbility(), ""),
    YoungBerserker("Young Berserker", 2, 3, RangedCombat, new Berserker(),
            "Transforms into Young Vidkaarl when Mardroeme is used.Young Vidkaarl has Tight Bond effect."),
    YoungVidkaarl("Young Vidkaarl", 8, 0, RangedCombat, new TightBond(),
            "it can only be played when a young berserker transform into it."),
    LightLongship("Light Longship", 4, 3, RangedCombat, new Muster(), ""),
    WarLongship("War Longship", 6, 3, Siege, new TightBond(), ""),
    DraigBonDhu("Draig Bon-Dhu", 2, 1, Siege, new CommanderHorn(), ""),
    Olaf("Olaf", 12, 1, Agile, new MoralBoost(), "");
    final String name;
    final int power;
    final int number;
    final Position position;
    final Ability ability;
    final String description;
    
    AllCards(String name, int power, int number, Position position, Ability ability, String description) {
        this.name = name;
        this.power = power;
        this.number = number;
        this.position = position;
        this.ability = ability;
        this.description = description;
    }

    public String getName() { return this.name; }

    public int getPower() { return this.power; }

    public int getNumber() { return this.number; }

    public Position getPosition() { return this.position; }

    public Ability getAbility() { return this.ability; }

    public String getDescription() { return this.description; }
}
