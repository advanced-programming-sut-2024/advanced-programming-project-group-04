package model.card.unitcards;
import model.ability.Ability;
import model.card.*;

public class UnitCard extends Card{
    private int power;

    public UnitCard (String name , Ability ability , int power) {
        super(name, ability);
        this.power = power;
    }
}
