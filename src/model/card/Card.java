package model.card;

import model.ability.Ability;

public class Card {
    private String name;
    private Ability ability;
    private String description;
    
    public Card (String name, String description, Ability ability) {
        this.name = name;
        this.ability = ability;
        this.description = description;
    }

    public String getName () {
        return this.name;
    }

    public Ability geAbility () {
        return this.ability;
    }

    public String getDescription() { return this.description; }
}
