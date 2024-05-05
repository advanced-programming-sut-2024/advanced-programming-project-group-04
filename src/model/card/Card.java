package model.card;

import model.ability.Ability;

public class Card {
    private String name;
    private Ability ability;
    
    public Card (String name , Ability ability) {
        this.name = name;
        this.ability = ability;
    }

    public String getName () {
        return this.name;
    }

    public Ability geAbility () {
        return this.ability;
    }
}
