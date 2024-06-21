package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.Ability;

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

    public void resetCurrentHP() {
        this.currentHP = getPower();
    }

    public String getName () {
        return this.allCard.getName();
    }

    public Ability getAbility () {
        return this.allCard.getAbility();
    }

    public String getDescription() { return this.allCard.getDescription(); }

    public Type getType() { return this.allCard.getType(); }

    public boolean isUnitCard() { return this.allCard.isUnitCard(); }
}