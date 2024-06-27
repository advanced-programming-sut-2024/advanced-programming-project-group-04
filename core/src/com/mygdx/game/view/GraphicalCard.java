package com.mygdx.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

import java.util.HashMap;

public class GraphicalCard extends ImageButton {
    private final Card card;

    public GraphicalCard(Drawable drawable, AllCards card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        Card newCard = new Card(card);
        this.card = newCard;
        allCardsCreated.put(newCard, this);
    }

    public GraphicalCard(Drawable drawable, Card card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = card;
        allCardsCreated.put(card, this);
    }

    public Card getCard() { return this.card; }
}
