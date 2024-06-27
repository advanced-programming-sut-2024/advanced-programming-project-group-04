package com.mygdx.game.model.card;

import com.mygdx.game.model.ability.*;

public class Card {
    private final AllCards allCard;
    private int currentHP;
    private int howManyMoralBoostAffected;
    private boolean isCommandersHornAffected;
    private boolean isWeathered;
    private int howManyTightBond;


    public Card(AllCards allCard) {
        this.allCard = allCard;
        this.currentHP = getPower();
        this.howManyMoralBoostAffected = 0;
        this.isCommandersHornAffected = false;
        this.isWeathered = false;
        this.howManyMoralBoostAffected = 1;
    }

    public int getHowManyTightBond() {
        return this.howManyTightBond;
    }

    public void setHowManyTightBond(int number) {
        this.howManyTightBond = number;
    }

    public boolean isCardsAbilityPassive() {
        return allCard.isCardsAbilityPassive();
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
        this.howManyMoralBoostAffected = 1;
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
        if (this.getName().equals(card.getName())) { return true; }
        if (getName().startsWith("Vampire") && card.getName().startsWith("Vampire")) {
            return true;
        }
        if (getName().startsWith("Crone") && card.getName().startsWith("Crone")) {
            return true;
        }
        if (allCard.equals(AllCards.GaunterODimm) && card.getAllCard().equals(AllCards.GaunterODimmDarkness)) {
            return true;
        }
        if (allCard.equals(AllCards.Cerys) && card.getAllCard().equals(AllCards.ClanDrummondShieldMaiden)) {
            return true;
        }
        return false;
    }

    public boolean isBerserker() {
        return this.getAbility() instanceof Berserker;
    }

    public boolean isHero() {
        return this.allCard.isHero();
    }

    public int calculateCurrentHP() {
        int calculatedHP = getPower();
        if (isHero()) {
            return calculatedHP;
        }
        if (isWeathered) {
            calculatedHP = 1;
        }
        int commandersAffect = isCommandersHornAffected ? 2 : 1;
        return ((calculatedHP + howManyMoralBoostAffected) * howManyTightBond) * commandersAffect;
    }

}