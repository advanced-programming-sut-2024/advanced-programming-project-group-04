package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.Ability;

public class Card {
    private String name;
    private Ability ability;
    private String description;

    public Card(AllCards allCard) {
        this.name = allCard.getName();
        this.ability = allCard.getAbility();
        this.description = allCard.getDescription();
    }

    public String getName () {
        return this.name;
    }

    public Ability geAbility () {
        return this.ability;
    }

    public String getDescription() { return this.description; }
}
