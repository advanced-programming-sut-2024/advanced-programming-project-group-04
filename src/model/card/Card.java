package model.card;

import model.ability.Ability;

public class Card {
    private String name;
    private Ability ability;
    private String description;
    private int power;

    private int currentHP;

    public Card(AllCards allCard) {
        this.name = allCard.getName();
        this.ability = allCard.getAbility();
        this.description = allCard.getDescription();
        this.power = allCard.getPower();
        this.currentHP = power;
    }

    public String getName () {
        return this.name;
    }

    public Ability geAbility () {
        return this.ability;
    }

    public String getDescription() { return this.description; }

    public int getPower() {
        return this.power;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public void setCurrentHP(int HP) {
        this.currentHP = HP; 
    }
}
