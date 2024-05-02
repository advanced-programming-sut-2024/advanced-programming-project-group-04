package model.Card.UnitCards;
import model.Ability.Ability;
import model.Card.*;

public class UnitCard extends Card{
    private int power;

    public UnitCard (String name , Ability ability , int power) {
        super(name, ability);
        this.power = power;
    }
}
