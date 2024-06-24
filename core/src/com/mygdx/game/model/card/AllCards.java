package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.*;

import static com.mygdx.game.model.card.Type.*;

public enum AllCards {
    Mardoeme("Mardoeme", 0, 3, Spell, new Mardroeme(), false,
            "This Spell card has Mardoeme Ability and can be placed in the commmander's Horn spot . it effects the row that it was placed in"),
    Berserker("Berserker", 4, 1, CloseCombat, new Berserker(), false,
            "Transforms into Vidkaarl when Mardroeme is used.Vidkaarl has Morale Boost effect."),
    Vidkaarl("Vidkaarl", 14, 0, CloseCombat, new MoralBoost(), false,
            "it can only be played when a berserker transform into it."),
    Svanrige("Svanrige", 4, 1, CloseCombat, new NoAbility(), false, ""),
    Udalryk("Udalryk", 4, 1, CloseCombat, new NoAbility(), false, ""),
    DonarAnHindar("Donar an Hindar", 4, 1, CloseCombat, new NoAbility(), false, ""),
    ClanAnCraite("Clan An Craite", 6, 3, CloseCombat, new TightBond(), false, ""),
    MadmanLugos("Madman Lugos", 6, 1, CloseCombat, new NoAbility(), false, ""),
    Cerys("Cerys", 10, 1, CloseCombat, new Muster(), true,
            "Its Muster effect will summon Shield Maiden cards and other musters."),
    Kambi("Kambi", 11, 1, CloseCombat, new Transformers(), true,
            "Turns into a card with a power of 8 after one round"),
    BirnaBran("Birna Bran", 2, 1, CloseCombat, new Medic(), false, ""),
    ClanDrummondShieldmaiden("Clan Drummond Shieldmaiden", 4, 3, CloseCombat, new TightBond(), false,
            "Can be Mustered by Cerys."),
    ClanDimunPirate("Clan Dimun Pirate", 6, 1, RangedCombat, new Scorch(), false,
            "kills opponent card(s) with most power(does not matter in which row in this card)"),
    ClanBrokvarArcher("Clan Brokvar Archer", 6, 3, RangedCombat, new NoAbility(), false, ""),
    Ermion("Ermion", 8, 1, RangedCombat, new Mardroeme(), true, ""),
    Hjalmar("Hjalmar", 10, 1, RangedCombat, new NoAbility(), true, ""),
    YoungBerserker("Young Berserker", 2, 3, RangedCombat, new Berserker(), false,
            "Transforms into Young Vidkaarl when Mardroeme is used.Young Vidkaarl has Tight Bond effect."),
    YoungVidkaarl("Young Vidkaarl", 8, 0, RangedCombat, new TightBond(), false,
            "it can only be played when a young berserker transform into it."),
    LightLongship("Light Longship", 4, 3, RangedCombat, new Muster(), false, ""),
    WarLongship("War Longship", 6, 3, Siege, new TightBond(), false, ""),
    DraigBonDhu("Draig Bon-Dhu", 2, 1, Siege, new CommanderHorn(), false, ""),
    Olaf("Olaf", 12, 1, Agile, new MoralBoost(), false, "");
    final String name;
    final int power;
    final int number;
    final Type type;
    final Ability ability;
    final String description;
    final boolean isHero;
    
    AllCards(String name, int power, int number, Type type, Ability ability, boolean isHero, String description) {
        this.name = name;
        this.power = power;
        this.number = number;
        this.type = type;
        this.ability = ability;
        this.description = description;
        this.isHero = isHero;
    }

    public String getName() { return this.name; }

    public int getPower() { return this.power; }

    public int getNumber() { return this.number; }

    public Type getType() { return this.type; }

    public Ability getAbility() { return this.ability; }

    public String getDescription() { return this.description; }

    public boolean isHero() { return this.isHero; }

    public boolean isUnitCard() {
        return this.type != Spell && this.type != Weather;
    }

    public String getImageUrl() {
        return "images/cards/" + this.name() + ".jpg";
    }
}
