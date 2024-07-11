package mygdx.game.model;

import mygdx.game.model.card.Card;

import java.util.ArrayList;

public class CardsInBoard {
    private ArrayList<Card> allCardsInGame;

    public CardsInBoard(GameManager gameManager) {
        allCardsInGame = new ArrayList<>(gameManager.getAllCards());
    }

    public ArrayList<Card> getAllCardsInGame() { return this.allCardsInGame; }
}
