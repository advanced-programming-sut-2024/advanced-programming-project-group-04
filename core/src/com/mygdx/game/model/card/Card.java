package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.*;

public class Card {
    private final AllCards allCard;
    private int currentHP;
    private int howManyMoralBoostAffected;
    private boolean isCommandersHornAffected;
    private boolean isWeathered;


    public Card(AllCards allCard) {
        this.allCard = allCard;
        this.currentHP = getPower();
        this.howManyMoralBoostAffected = 0;
        this.isCommandersHornAffected = false;
        this.isWeathered = false;
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

    public int getHowManyMoralBoostAffected() {
        return this.howManyMoralBoostAffected;
    }

    public void setHowManyMoralBoostAffected(int number) {
        this.howManyMoralBoostAffected = number;
    }

    public boolean getIsCommandersHornAffected() {
        return this.isCommandersHornAffected;
    }

    public void setIsCommandersHornAffected(boolean bool) {
        this.isCommandersHornAffected = bool;
    }

    public boolean getIsWeathered() {
        return this.isWeathered;
    }

    public void setIsWeathered(boolean bool) {
        this.isWeathered = bool;
    }

    public void resetCard() {
        this.currentHP = getPower();
        this.howManyMoralBoostAffected = 0;
        this.isCommandersHornAffected = false;
        this.isWeathered = false;
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

    public AllCards getAllCard() { return this.allCard; }

    public boolean musterEquality(Card card) {
        // TODO
        // return true if THIS have muster ability and
        // THIS calls the card
        return false;
    }

    public boolean isBerserker() {
        return this.getAbility() instanceof Berserker;
    }

    public boolean isHero() {
        return this.allCard.isHero();
    }

}