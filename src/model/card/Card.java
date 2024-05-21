package model.card;

import model.ability.Ability;

public class Card {
    private final AllCards allCard;
    private int currentHP;

    public Card(AllCards allCard) {
        this.allCard = allCard;
        this.currentHP = getPower();
    }

    public int getPower() {
        return this.allCard.getPower();
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public void setCurrentHP(int HP) {
        this.currentHP = HP;
    }

    public String getName () {
        return this.allCard.getName();
    }

    public Ability geAbility () {
        return this.allCard.getAbility();
    }

    public String getDescription() { return this.allCard.getDescription(); }

    public boolean isUnitCard() { return this.allCard.isUnitCard(); }
}
