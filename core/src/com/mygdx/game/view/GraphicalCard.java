package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

import java.util.HashMap;

public class GraphicalCard extends ImageButton {
    private final Card card;
    private final ShapeRenderer shapeRenderer;

    public GraphicalCard(Drawable drawable, AllCards card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = new Card(card);
        allCardsCreated.put(this.card, this);
        shapeRenderer = new ShapeRenderer();
    }

    public GraphicalCard(Drawable drawable, Card card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = card;
        allCardsCreated.put(this.card, this);
        shapeRenderer = new ShapeRenderer();
    }

    public Card getCard() {
        return this.card;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the card image
        super.draw(batch, parentAlpha);

        // End the batch so we can draw shapes
        batch.end();

        // Calculate the position and size of the circle
        float centerX = getX() + getWidth() / 2;
        float centerY = getY() + getHeight() / 2;
        float radius = Math.min(getWidth(), getHeight()) / 4; // Adjust the radius as needed

        // Draw the white circle
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.end();

        // Restart the batch to continue drawing other elements
        batch.begin();
    }
}
