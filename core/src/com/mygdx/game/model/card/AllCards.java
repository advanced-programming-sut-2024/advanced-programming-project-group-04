package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.*;

import static com.mygdx.game.model.card.Type.*;

public enum AllCards {
    // Skellige

    Mardroeme("Mardroeme", 0, 3, Spell, new Mardroeme(), false, "This Spell card has Mardroeme Ability and can be placed in the commmander's Horn spot . it effects the row that it was placed in"),
    Berserker("Berserker", 4, 1, CloseCombat, new Berserker(), false, "Transforms into Vidkaarl when Mardroeme is used.Vidkaarl has Morale Boost effect."),
    TransformedVidkaarl("Transformed Vidkaarl", 14, 0, CloseCombat, new MoralBoost(), false, "it can only be played when a berserker transform into it."),
    Svanrige("Svanrige", 4, 1, CloseCombat, new NoAbility(), false, ""),
    Udalryk("Udalryk", 4, 1, CloseCombat, new NoAbility(), false, ""),
    DonarAnHindar("Donar an Hindar", 4, 1, CloseCombat, new NoAbility(), false, ""),
    ClanAnCraite("Clan An Craite", 6, 3, CloseCombat, new TightBond(), false, ""),
    BlueboyLugos("Blueboy Lugos", 6, 1, CloseCombat, new NoAbility(), false, ""),
    MadmanLugos("Madman Lugos", 6, 1, CloseCombat, new NoAbility(), false, ""),
    Cerys("Cerys", 10, 1, CloseCombat, new Muster(), true, "Its Muster effect will summon Shield Maiden cards and other musters."),
    Kambi("Kambi", 0, 1, CloseCombat, new Transformers(), true, "Turns into a card with a power of 8 after one round"),
    BirnaBran("Birna Bran", 2, 1, CloseCombat, new Medic(), false, ""),
    ClanDrummondShieldMaiden("Clan Drummond Shieldmaiden", 4, 3, CloseCombat, new TightBond(), false, "Can be Mustered by Cerys."),
    ClanTordarrochArmorsmith("Clan Tordarroch Armorsmith", 4, 1, CloseCombat, new NoAbility(), false, ""),
    ClanDimunPirate("Clan Dimun Pirate", 6, 1, RangedCombat, new Scorch(), false, "kills opponent card(s) with most power(does not matter in which row in this card)"),
    ClanBrokvarArcher("Clan Brokvar Archer", 6, 3, RangedCombat, new NoAbility(), false, ""),
    Ermion("Ermion", 8, 1, RangedCombat, new Mardroeme(), true, ""),
    Hjalmar("Hjalmar", 10, 1, RangedCombat, new NoAbility(), true, ""),
    YoungBerserker("Young Berserker", 2, 3, RangedCombat, new Berserker(), false, "Transforms into Young Vidkaarl when Mardroeme is used.Young Vidkaarl has Tight Bond effect."),
    TransformedYoungVidkaarl("Transformed Young Vidkaarl", 8, 0, RangedCombat, new TightBond(), false, "it can only be played when a young berserker transform into it."),
    LightLongship("Light Longship", 4, 3, RangedCombat, new Muster(), false, ""),
    HolgerBlackhand("Holger Blackhand", 4, 1, Siege, new NoAbility(), false, ""),
    WarLongship("War Longship", 6, 3, Siege, new TightBond(), false, ""),
    DraigBonDhu("Draig Bon-Dhu", 2, 1, Siege, new CommanderHorn(), false, ""),
    Olaf("Olaf", 12, 1, Agile, new MoralBoost(), false, ""),
    Hemdall("Hemdall", 11, 0, CloseCombat, new NoAbility(), true, ""), // Not in Dec
    ClanHeymaeySkald("Clan Heymaey Skald", 4, 1, CloseCombat, new NoAbility(), false, ""), // Not in Dec

    // Scoiatael

    ElvenSkirmisher("Elven Skirmisher", 2, 3, RangedCombat, new Muster(), false, ""),
    Iorveth("Iorveth", 10, 1, RangedCombat, new NoAbility(), true, ""),
    Yaevinn("Yaevinn", 6, 1, Agile, new NoAbility(), false, ""),
    CiaranAepEasnillien("Ciaran aep Easnillien", 3, 1, Agile, new NoAbility(), false, ""), // Changed The Name
    DennisCranmer("Dennis Cranmer", 6, 1, CloseCombat, new NoAbility(), false, ""),
    DolBlathannaScout("Dol Blathanna Scout", 6, 3, Agile, new NoAbility(), false, ""),
    DolBlathannaArcher("Dol Blathanna Archer", 4, 1, RangedCombat, new NoAbility(), false, ""),
    DwarvenSkirmisher("Dwarven Skirmisher", 3, 3, CloseCombat, new Muster(), false, ""),
    FilavandrelAenFidhail("FilavandrelAenFidhail", 6, 1, Agile, new NoAbility(), false, ""),
    HavekarHealer("Havekar Healer", 0, 3, RangedCombat, new Medic(), false, ""),
    HavekarSmuggler("Havekar Smuggler", 5, 3, CloseCombat, new Muster(), false, ""),
    IdaEmeanAepSivney("Ida Emean aep", 6, 1, RangedCombat, new NoAbility(), false, ""),
    Riordain("Riordain", 1, 1, RangedCombat, new NoAbility(), false, ""),
    Toruviel("Toruviel", 2, 1, RangedCombat, new NoAbility(), false, ""),
    VriheddBrigadeRecruit("Vrihedd Brigade Recruit", 4, 1, RangedCombat, new NoAbility(), false, ""),
    MahakamanDefender("Mahakaman Defender", 5, 5, CloseCombat, new NoAbility(), false, ""),
    VriheddBrigadeVeteran("Vrihedd Brigade Veteran", 5, 2, Agile, new NoAbility(), false, ""),
    Milva("Milva", 10, 1, RangedCombat, new MoralBoost(), false, ""),
    Saesenthessis("Saesenthessis", 10, 1, RangedCombat, new NoAbility(), true, ""),
    Schirru("Schirru", 8, 1, Siege, new Scorch(), false, "kills the opponent's card(s) with most power in enemy's Siege combat row if the sum of powers of none-hero cards in this row is 10 or more"),
    BarclayEls("Barclay Els", 6, 1, Agile, new NoAbility(), false, "-"),
    Eithne("Eithne", 10, 1, RangedCombat, new NoAbility(), true, ""),
    IsengrimFaolitarna("Isengrim Faoiltiarna", 10, 1, CloseCombat, new MoralBoost(), true, ""),

    // Northen Realms

    Ballista("Ballista", 6, 2, Siege, new NoAbility(), false, ""),
    BlueStripesCommando("Blue Stripes Commando", 4, 3, CloseCombat, new TightBond(), false, ""),
    Catapult("Catapult", 8, 2, Siege, new TightBond(), false, ""),
    DragonHunter("Dragon Hunter", 5, 3, RangedCombat, new TightBond(), false, ""),
    Dethmold("Dethmold", 6, 1, RangedCombat, new NoAbility(), false, ""),
    DunBannerMedic("Dun Banner Medic", 5, 1, Siege, new Medic(), false, ""),
    EsteradThyssen("Esterad Thyssen", 10, 1, CloseCombat, new NoAbility(), true, ""),
    JohnNatalis("John Natalis", 10, 1, CloseCombat, new NoAbility(), true, ""),
    KaedweniSiegeExpert("Kaedweni Siege Expert", 1, 3, Siege, new MoralBoost(), false, ""),
    KeiraMetz("Keira Metz", 5, 1, RangedCombat, new NoAbility(), false, ""),
    PhilippaEilhart("Philippa Eilhart", 10, 1, RangedCombat, new NoAbility(), true, ""),
    PoorFuckingInfantry("Poor Fucking Infantry", 1, 4, CloseCombat, new TightBond(), false, ""),
    PrinceStennis("Prince Stennis", 5, 1, CloseCombat, new Spy(), false, ""),
    RedanianFootSoldier("Redanian Foot Soldier", 1, 2, CloseCombat, new NoAbility(), false, ""),
    SabrinaGlevissig("Sabrina Glevissing", 4, 1, RangedCombat, new NoAbility(), false, ""),
    SheldonSkaggs("Sheldon Skaggs", 4, 1, RangedCombat, new NoAbility(), false, ""),
    SiegeTower("Siege Tower", 6, 1, Siege, new NoAbility(), false, ""),
    SiegfriedOfDenesle("Siegfried of Denesle", 5, 1, CloseCombat, new NoAbility(), false, ""),
    SigismundDijkstra("Sigismund Dijkstra", 4, 1, CloseCombat, new Spy(), false, ""),
    SileDeTansarville("Síle de Tansarville", 5, 1, RangedCombat, new NoAbility(), false, ""),
    Thaler("Thaler", 1, 1, Siege, new Spy(), false, ""),
    Trebuchet("Trebuchet", 6, 2, Siege, new NoAbility(), false, ""),
    VernonRoche("Vernon Roche", 10, 1, CloseCombat, new NoAbility(), true, ""),
    Ves("Ves", 5, 1, CloseCombat, new NoAbility(), false, ""),
    YarpenZigrin("Yarpen Zirgrin", 2, 1, CloseCombat, new NoAbility(), false, ""),

    // Milf gaard

    ImperaBrigadeGuard("Impera Brigade Guard", 3, 4, CloseCombat, new TightBond(), false, ""),
    StefanSkellen("Stefan Skellen", 9, 1, CloseCombat, new Spy(), false, ""),
    ShilardFitzOesterlen("Shilard Fitz-Oesterlen", 7, 1, CloseCombat, new Spy(), false, ""),
    YoungEmissary("Young Emissary", 5, 2, CloseCombat, new TightBond(), false, ""),
    CahirMawrDyffrynAepCeallach("Cahir Mawr Dyffryn aep Ceallach", 6, 1, CloseCombat, new NoAbility(), false, ""),
    VattierDeRideaux("Vattier de Rideaux", 4, 1, CloseCombat, new Spy(), false, ""),
    Puttkammer("Puttkammer", 3, 1, RangedCombat, new NoAbility(), false, ""),
    AssireVarAnahid("Assire var Anahid", 6, 1, RangedCombat, new NoAbility(), false, ""),
    BlackInfantryArcher("Black Infantry Archer", 10, 2, RangedCombat, new NoAbility(), false, ""),
    TiborEggebracht("Tibor Eggebracht", 10, 1, RangedCombat, new NoAbility(), true, ""),
    RenualdAepMatsen("Renuald aep Matsen", 5, 1, RangedCombat, new NoAbility(), false, ""),
    FringillaVigo("Fringilla Vigo", 6, 1, RangedCombat, new NoAbility(), false, ""),
    RottenMangonel("Rotten Mangonel", 3, 1, Siege, new NoAbility(), false, ""),
    HeavyZerrikanianFireScorpion("Heavy Zerrikanian Fire Scorpion", 10, 1, Siege, new NoAbility(), false, ""),
    ZerrikanianFireScorpion("Zerrikanian Fire Scorpion", 5, 1, Siege, new NoAbility(), false, ""),
    SiegeEngineer("Siege Engineer", 6, 1, Siege, new NoAbility(), false, ""),
    Albrich("Albrich", 2, 1, RangedCombat, new NoAbility(), false, ""),
    Cynthia("Cynthia", 4, 1, RangedCombat, new NoAbility(), false, ""),
    EtolianAuxiliaryArchers("Etolian Auxiliary Archers ", 1, 2, RangedCombat, new Medic(), false, ""),
    LethoOfGulet("Letho of Gulet", 10, 1, CloseCombat, new NoAbility(), true, ""),
    MennoCoehoorn("Menno Coehoorn", 10, 1, CloseCombat, new Medic(), true, ""),
    Morteisen("Morteisen", 3, 1, CloseCombat, new NoAbility(), false, ""),
    MorvranVoorhis("Morvran Voorhis", 10, 1, Siege, new NoAbility(), true, ""),
    NausicaaCavalryRider("Nausicaa Cavalry Rider", 2, 3, CloseCombat, new TightBond(), false, ""),
    Rainfarn("Rainfarn ", 4, 1, CloseCombat, new NoAbility(), false, ""),
    SiegeTechnician("Siege Technician", 0, 1, Siege, new Medic(), false, ""),
    Sweers("Sweers", 2, 1, RangedCombat, new NoAbility(), false, ""),
    Vanhemar("Vanhemar", 4, 1, RangedCombat, new NoAbility(), false, ""),
    Vreemde("Vreemde", 2, 0, CloseCombat, new NoAbility(), false, ""),

    // Monsters

    Draug("Draug", 10, 1, CloseCombat, new NoAbility(), true, ""),
    Imlerith("Imlerith", 10, 1, CloseCombat, new NoAbility(), true, ""),
    Leshen("Leshen", 10, 1, RangedCombat, new NoAbility(), true, ""),
    Kayran("Kayran", 8, 1, Agile, new MoralBoost(), true, ""),
    Toad("Toad", 7, 1, RangedCombat, new Scorch(), false, "kills the opponent's card(s) with most power in enemy's Ranged combat row if the sum of powers of none-hero cards in this row is 10 or more"),
    ArachasBehemoth("Arachas Behemoth", 6, 1, Siege, new Muster(), false, ""),
    CroneBrewess("Crone: Brewess", 6, 1, CloseCombat, new Muster(), false, ""),
    CroneWeavess("Crone: Weavess", 6, 1, CloseCombat, new Muster(), false, ""),
    CroneWhispess("Crone: Whispess", 6, 1, CloseCombat, new Muster(), false, ""),
    EarthElemental("Earth Elemental", 6, 1, Siege, new NoAbility(), false, ""),
    Fiend("Fiend", 6, 1, CloseCombat, new NoAbility(), false, ""),
    FireElemental("Fire Elemental", 6, 1, Siege, new NoAbility(), false, ""),
    Forktail("Forktail", 5, 1, CloseCombat, new NoAbility(), false, ""),
    Frightener("Frightener", 5, 1, CloseCombat, new NoAbility(), false, ""),
    GraveHag("Grave Hag", 5, 1, RangedCombat, new NoAbility(), false, ""),
    Griffin("Griffin", 5, 1, CloseCombat, new NoAbility(), false, ""),
    IceGiant("Ice Giant", 5, 1, Siege, new NoAbility(), false, ""),
    PlagueMaiden("Plague Maiden", 5, 1, CloseCombat, new NoAbility(), false, ""),
    VampireKatakan("Vampire: Katakan", 5, 1, CloseCombat, new Muster(), false, ""),
    Werewolf("Werewolf", 5, 1, CloseCombat, new NoAbility(), false, ""),
    Arachas("Arachas", 4, 3, CloseCombat, new Muster(), false, ""),
    Botchling("Botchling", 4, 1, CloseCombat, new NoAbility(), false, ""),
    VampireBruxa("Vampire: Bruxa", 4, 1, CloseCombat, new Muster(), false, ""),
    VampireEkimmara("Vampire: Ekimmara", 4, 1, CloseCombat, new Muster(), false, ""),
    VampireFleder("Vampire: Fleder", 4, 1, CloseCombat, new Muster(), false, ""),
    VampireGarkain("Vampire: Garkain", 4, 1, CloseCombat, new Muster(), false, ""),
    CelaenoHarpy("Celaeno Harpy", 2, 1, Agile, new NoAbility(), false, ""),
    Cockatrice("Cockatrice", 2, 1, RangedCombat, new NoAbility(), false, ""),
    Endrega("Endrega", 2, 1, RangedCombat, new NoAbility(), false, ""),
    Foglet("Foglet", 2, 1, CloseCombat, new NoAbility(), false, ""),
    Gargoyle("Gargoyle", 2, 1, RangedCombat, new NoAbility(), false, ""),
    Harpy("Harpy", 2, 1, Agile, new NoAbility(), false, ""),
    Nekker("Nekker", 2, 3, CloseCombat, new Muster(), false, ""),
    Wyvern("Wyvern", 2, 1, RangedCombat, new NoAbility(), false, ""),
    Ghoul("Ghoul", 1, 3, CloseCombat, new Muster(), false, ""),

    // Neutral

    BitingFrost("Biting Frost", 0, 3, Weather, new BitingFrost(), false, "Sets the power of all close combat units of both sides to 1"),
    ImpenetrableFog("Impenetrable fog", 0, 3, Weather, new ImpenetrableFog(), false, "Sets the power of all ranged units of both sides to 1"),
    TorrentialRain("Torrential Rain", 0, 3, Weather, new TorrentialRain(), false, "Sets the power of all siege units of both sides to 1"),
    SkelligeStorm("Skellige Storm", 0, 3, Weather, new SkelligeStrom(), false, "Sets the power of all siege and ranged units of both sides to 1"),
    ClearWeather("Clear Weather", 0, 3, Weather, new ClearWeather(), false, "Cancel all the weather cards"),
    Scorch("Scorch", 0, 3, Spell, new NoAbility(), false, "Remove card(s) with the maximum power points in the field (ignores heroes)"),
    CommandersHorn("Commanders horn", 0, 3, Spell, new CommanderHorn(), false, "Doubles the power of the cards in the row that was placed. Only one of this type can be played in a row"),
    Decoy("Decoy", 0, 3, Spell, new NoAbility(), false, "The description has been given before"), // Chenged
    Dandelion("Dandelion", 2, 1, CloseCombat, new CommanderHorn(), false, "Same power as the commander's horn"),
    Cow("Cow", 0, 1, RangedCombat, new NoAbility(), false, "Turns into a card with a power of 8 after one round"),
    EmielRegis("Emiel Regis", 5, 1, CloseCombat, new NoAbility(), false, ""),
    GaunterODimm("Gaunter O'Dimm", 2, 1, Siege, new Muster(), false, ""),
    GaunterODimmDarkness("Gaunter O'Dimm Darkness", 4, 3, RangedCombat, new Muster(), false, ""),
    GeraltOfRivia("Geralt of Rivia", 15, 1, CloseCombat, new NoAbility(), true, ""),
    Avallach("Avallach", 0, 1, CloseCombat, new Spy(), true, ""),
    OlgierdVonEverec("Olgierd Von Everc", 6, 1, Agile, new MoralBoost(), false, ""),
    TrissMerigold("Triss Merigold", 7, 1, CloseCombat, new NoAbility(), true, ""),
    Vesemir("Vesemir", 6, 1, CloseCombat, new NoAbility(), false, "-"),
    Villentretenmerth("Villentretenmerth", 7, 1, CloseCombat, new Scorch(), false, "Works only on opponent's close combat"),
    YenneferOfVengerberg("Yennefer of Vengerberg", 7, 1, RangedCombat, new Medic(), true, ""),
    ZoltanChivay("Zoltan Chivay", 5, 1, CloseCombat, new NoAbility(), false, ""),
    BovineDefenseForce("Bovine Defense Force", 8, 0, CloseCombat, new NoAbility(), false, ""), // Not in Dec
    CirillaFionaElenRiannon("Cirilla Fiona Elen Riannon", 15, 1, CloseCombat, new NoAbility(), true, "") // Not in Dec
    ;


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

    public String getName() {
        return this.name;
    }

    public int getPower() {
        return this.power;
    }

    public int getNumber() {
        return this.number;
    }

    public Type getType() {
        return this.type;
    }

    public Ability getAbility() {
        return this.ability;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isHero() {
        return this.isHero;
    }

    public boolean isUnitCard() {
        return this.type != Spell && this.type != Weather;
    }

    public boolean isWeather() {
        return type == Weather;
    }

    public boolean isCardsAbilityPassive() {
        return ability instanceof CommanderHorn
                || ability instanceof MoralBoost
                || ability instanceof TightBond
                || isWeather()
                ;
    }

    public String getImageURL() {
        return "images/cards/" + this.name() + ".jpg";
    }

}
