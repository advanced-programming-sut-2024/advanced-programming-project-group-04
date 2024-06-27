package com.mygdx.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

public class GraphicalCard extends ImageButton {
    private final Card card;

    public GraphicalCard(Drawable drawable, AllCards card) {
        super(drawable);
        this.card = new Card(card);
    }

    public GraphicalCard(Drawable drawable, Card card) {
        super(drawable);
        this.card = card;
    }

    public Card getCard() { return this.card; }
}
