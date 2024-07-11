package mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import mygdx.game.AssetLoader;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalCard extends ImageButton {
    private final Card card;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;

    private final Label labelInsideCircle;

    public GraphicalCard(Drawable drawable, AllCards card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = new Card(card);
        allCardsCreated.put(this.card, this);
        shapeRenderer = new ShapeRenderer();
        font = AssetLoader.getFontWithCustomSize(3); // Initialize the BitmapFont
        font.setColor(Color.BLACK);
        labelInsideCircle = new Label("salam", new Label.LabelStyle(font, Color.BLACK));
        labelInsideCircle.setFontScale(3);
    }

    public GraphicalCard(Drawable drawable, Card card, HashMap<Card, GraphicalCard> allCardsCreated) {
        super(drawable);
        this.card = card;
        allCardsCreated.put(this.card, this);
        shapeRenderer = new ShapeRenderer();
        font = AssetLoader.getFontWithCustomSize(18); // Initialize the BitmapFont
        font.setColor(Color.BLACK);
        labelInsideCircle = new Label("salam", new Label.LabelStyle(font, Color.BLACK));
    }

    public Card getCard() {
        return this.card;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        float constant = 8f;
        float centerX = getX() + getWidth() * ((1 / constant) + 0.08f);
        float centerY = getY() + getHeight() * ((constant - 1) / constant + 0.05f);
        float radius = Math.min(getWidth(), getHeight()) / 10;

        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.end();

        labelInsideCircle.setText(card.getCurrentHP());
        if (card.getCurrentHP() > card.getPower()) {
            labelInsideCircle.setStyle(new Label.LabelStyle(font, Color.GREEN));
        } else if (card.getCurrentHP() < card.getPower()) {
            labelInsideCircle.setStyle(new Label.LabelStyle(font, Color.RED));
        } else {
        }

        float widthError = labelInsideCircle.getWidth() * (0.5f + 0.025f);
        float heightError = labelInsideCircle.getHeight() * (0.5f - 0.1f);
        labelInsideCircle.setPosition(centerX - widthError, centerY - heightError);
        labelInsideCircle.setAlignment(Align.center);
        getStage().addActor(labelInsideCircle);
        batch.begin();
    }

    public Label getLabelInsideCircle() {
        return labelInsideCircle;
    }

    public void update(ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getId() == this.card.getId()) {
                this.card.setCurrentHP(card.getCurrentHP());
                return;
            }
        }
        throw new RuntimeException("Card HP not found in row");
    }

    @Override
    public boolean remove() {
        super.remove();
        this.labelInsideCircle.remove();
        return true;
    }
}
