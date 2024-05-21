package model.card;

import model.ability.Ability;
import model.ability.Berserker;

import static model.card.CardType.*;

public enum AllCards {
    ARVIN("Arvin", -69, 85, GAY, new Berserker());
    String name;
    int power;
    int number;
    CardType type;
    Ability ability;
    AllCards(String name, int power, int number, CardType type, Ability ability) {
        this.name = name;
        this.power = power;
        this.number = number;
        this.type = type;
        this.ability = ability;
    }
}
