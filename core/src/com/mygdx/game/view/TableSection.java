package mygdx.game.view;

import mygdx.game.model.Position;

public enum TableSection {
    MY_HAND("My Hand", false, false, null),
    MY_DECK("My Deck", false, false, null),
    MY_GRAVEYARD("My Graveyard", false, false, null),
    MY_LEADER("My Leader", false, false, null),
    MY_SPELL_RANGE("My Spell Range", true, false, Position.SpellRange),
    MY_SPELL_SIEGE("My Spell Siege", true, false, Position.SpellSiege),
    MY_SPELL_MELEE("My Spell Melee", true, false, Position.SpellMelee),
    MY_RANGE("My Range", true, false, Position.Range),
    MY_SIEGE("My Siege", true, false, Position.Siege),
    MY_MELEE("My Melee", true, false, Position.Melee),
    ENEMY_HAND("Enemy Hand", false, true, null),
    ENEMY_DECK("Enemy Deck", false, true, null),
    ENEMY_GRAVEYARD("Enemy Graveyard", false, true, null),
    ENEMY_LEADER("Enemy Leader", false, true, null),
    ENEMY_SPELL_RANGE("Enemy Spell Range", true, true, Position.SpellRange),
    ENEMY_SPELL_SIEGE("Enemy Spell Siege", true, true, Position.SpellSiege),
    ENEMY_SPELL_MELEE("Enemy Spell Melee", true, true, Position.SpellMelee),
    ENEMY_RANGE("Enemy Range", true, true, Position.Range),
    ENEMY_SIEGE("Enemy Siege", true, true, Position.Siege),
    ENEMY_MELEE("Enemy Melee", true, true, Position.Melee),
    WEATHER("Weather", true, false, Position.WeatherPlace);
    final String title;
    final boolean canCardBePlaced;
    final boolean isEnemy;
    final Position position;

    TableSection(String title, boolean canCardBePlaced, boolean isEnemy, Position position) {
        this.title = title;
        this.canCardBePlaced = canCardBePlaced;
        this.isEnemy = isEnemy;
        this.position = position;
    }

    public boolean canPlaceCard() {
        return this.canCardBePlaced;
    }

    public boolean isEnemy() {
        return this.isEnemy;
    }

    public Position getPosition() {
        return this.position;
    }

    public String getTitle() {
        return this.title;
    }

    public static TableSection getTableSectionByPosition(Position position, boolean isEnemy) {
        if (position == Position.WeatherPlace) return TableSection.WEATHER;
        for (TableSection tableSection : TableSection.values()) {
            if (tableSection.getPosition() != null && tableSection.getPosition().equals(position) &&
                    tableSection.isEnemy() == isEnemy) return tableSection;
        }
        return null;
    }
}
