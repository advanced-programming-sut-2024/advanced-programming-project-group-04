package mygdx.game.model.data;

import mygdx.game.model.GameManager;
import mygdx.game.model.card.Card;

import java.io.Serializable;
import java.util.ArrayList;

public class CardsInBoard implements Serializable {
    private ArrayList<Card> allCardsInGame;

    public CardsInBoard(GameManager gameManager) {
        allCardsInGame = new ArrayList<>(gameManager.getAllCards());
    }

    public ArrayList<Card> getAllCardsInGame() { return this.allCardsInGame; }
}
