package com.mygdx.game.model.card.unitcards;
import com.mygdx.game.model.card.*;

public class UnitCard extends Card{
    private int power;
    private int currentHP;

    public UnitCard(AllCards allCard) {
        super(allCard);
        this.power = allCard.getPower();
        this.currentHP = power;
    }

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
