package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

import javax.swing.*;
import java.util.HashMap;

public class GraphicalCard extends ImageButton {
    private final Card card;
    private final ShapeRenderer shapeRenderer;
    private BitmapFont font;


    private Label labelInsideCircle;

    public GraphicalCard(Drawable drawable, AllCards card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = new Card(card);
        allCardsCreated.put(this.card, this);
        shapeRenderer = new ShapeRenderer();
        font = AssetLoader.getFontWithCustomSize(3); // Initialize the BitmapFont
        font.setColor(Color.BLACK); // Set font color to black
        labelInsideCircle = new Label("salam", new Label.LabelStyle(font, Color.BLACK));
        labelInsideCircle.setFontScale(3);
    }

    public GraphicalCard(Drawable drawable, Card card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = card;
        allCardsCreated.put(this.card, this);
        shapeRenderer = new ShapeRenderer();
        font = AssetLoader.getFontWithCustomSize(18); // Initialize the BitmapFont
        font.setColor(Color.BLACK); // Set font color to black
        labelInsideCircle = new Label("salam", new Label.LabelStyle(font, Color.BLACK));
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
        float constant = 8f;
        float centerX = getX() + getWidth() * ((1 / constant) + 0.08f);
        float centerY = getY() + getHeight() * ((constant - 1) / constant + 0.05f);
        float radius = Math.min(getWidth(), getHeight()) / 10; // Adjust the radius as needed

        // Draw the white circle
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.end();

        // text
        labelInsideCircle.setText(card.getCurrentHP());
        float widthError = labelInsideCircle.getWidth() * (0.5f + 0.025f) ;
        float heightError = labelInsideCircle.getHeight() * (0.5f - 0.1f);
        labelInsideCircle.setPosition(centerX - widthError, centerY - heightError);
        labelInsideCircle.setAlignment(Align.center);
        getStage().addActor(labelInsideCircle);
        // Restart the batch to continue drawing other elements
        batch.begin();

    }


    public Label getLabelInsideCircle() {
        return labelInsideCircle;
    }
}
