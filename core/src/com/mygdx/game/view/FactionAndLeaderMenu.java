package mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import mygdx.game.AssetLoader;
import mygdx.game.Main;
import mygdx.game.controller.FactionAndLeaderController;
import mygdx.game.model.card.AllCards;
import mygdx.game.model.card.Card;

import java.util.ArrayList;


public class FactionAndLeaderMenu extends Menu {
    private final FactionAndLeaderController controller;
    Skin skin;

    float buttonSize = 300;

    Table availableCardsTable = new Table();
    Table selectedCardsTable = new Table();
    Table statsTable = new Table();

    Label explanationLabel;
    Label heroCountLabel;
    Label unitCountLabel;
    Label spellCountLabel;

    ScrollPane availableCardsScrollPane;
    ScrollPane selectedCardsScrollPane;

    public FactionAndLeaderMenu(Main game) {
        super(game);
        this.controller = new FactionAndLeaderController(game, this);
        controller.loadData();

        skin = game.assetLoader.skin;
        stage.addActor(game.assetLoader.backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = game.assetLoader.labelStyle;
        explanationLabel = new Label("", labelStyle);
        explanationLabel.setAlignment(Align.center);
        explanationLabel.setWrap(true);
        float labelWidth = 2560 - 2 * 500;
        explanationLabel.setSize(labelWidth, explanationLabel.getPrefHeight());
        explanationLabel.setPosition(500, 200);

        stage.addActor(explanationLabel);

        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;
        TextButton chooseFactionButton = new TextButton("Choose Faction", textButtonStyle);
        TextButton chooseLeaderButton = new TextButton("Choose Leader", textButtonStyle);
        TextButton selectDeckButton = new TextButton("Select Deck", textButtonStyle);
        TextButton backButton = new TextButton("Back", textButtonStyle);
        chooseFactionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showFactionSelection();
            }
        });
        chooseLeaderButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLeaderSelection();
            }
        });
        selectDeckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDeckSelection();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new MainMenu(game));
            }
        });

        table.add(chooseFactionButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(chooseLeaderButton).width(400).height(120).pad(10);
        table.row().pad(20, 0, 20, 0);
        table.add(selectDeckButton).width(400).height(120).pad(10);
        table.row().pad(20, 0, 20, 0);
        table.add(backButton).pad(40).width(400).height(120);

        heroCountLabel = new Label("Number of Hero Cards: 0", labelStyle);
        unitCountLabel = new Label("Number of Unit Cards: 0", labelStyle);
        spellCountLabel = new Label("Number of Spell Cards: 0", labelStyle);
        updateStats();
    }

    private void showFactionSelection() {
        Dialog dialog = createNewDialog("Select Faction");

        Table factionTable = new Table();
        factionTable.center();

        // Load faction images using AssetLoader
        AssetLoader assetLoader = game.assetLoader;
        for (String factionPath : assetLoader.getFactions()) {
            Texture factionTexture = game.assetManager.get(factionPath, Texture.class);
            ImageButton factionButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(factionTexture)));
            String factionName = factionPath.substring(factionPath.lastIndexOf("/") + 1, factionPath.lastIndexOf("."));

            float buttonSize = 450;
            factionButton.getImageCell().size(buttonSize, buttonSize);

            // Add hover and click effects
            factionButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    factionButton.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));

                    String explanation = assetLoader.getFactionExplanation(factionPath);
                    explanationLabel.setText(explanation);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    factionButton.getImage().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    explanationLabel.setText("");
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.factionButtonClicked(factionName);
                    highlightSelectedButton(factionButton);
                    dialog.hide();
                }
            });

            if (controller.isThisFactionSelected(factionName)) {
                highlightSelectedButton(factionButton);
            }

            factionTable.add(factionButton).pad(20);
        }

        dialog.getContentTable().add(factionTable).pad(50);
        dialog.button("Cancel").pad(20);
        dialog.show(stage);
    }

    private void showLeaderSelection() {
        if (controller.noFactionSelected()) {
            System.out.println("Please select a faction first.");
            return;
        }

        Dialog dialog = createNewDialog("Select Leader");

        Table leaderTable = new Table();
        leaderTable.center();

        // Load leader images using AssetLoader
        AssetLoader assetLoader = game.assetLoader;
        for (String leaderPath : assetLoader.getLeaders(controller.getFactionAssetName())) {
            Texture leaderTexture = game.assetManager.get(leaderPath, Texture.class);
            ImageButton leaderButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leaderTexture)));
            String leaderName = leaderPath.substring(leaderPath.lastIndexOf("/") + 1, leaderPath.lastIndexOf("."));

            float buttonSize = 450;
            leaderButton.getImageCell().size(buttonSize, buttonSize);

            // Add hover and click effects
            leaderButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    leaderButton.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                    String explanation = assetLoader.getLeaderExplanation(leaderPath);
                    explanationLabel.setText(explanation);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    leaderButton.getImage().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    explanationLabel.setText("");
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.leaderButtonClicked(leaderName);
                    highlightSelectedButton(leaderButton);
                    dialog.hide();
                }
            });

            if (controller.isThisLeaderSelected(leaderName)) {
                highlightSelectedButton(leaderButton);
            }

            leaderTable.add(leaderButton).pad(20);
        }

        dialog.getContentTable().add(leaderTable).pad(50);
        dialog.button("Cancel").pad(20);
        dialog.show(stage);
    }

    private Dialog createNewDialog(String title) {
        Dialog dialog = new Dialog(title, skin);

        // Set dialog size
        float dialogWidth = Gdx.graphics.getWidth() * 0.8f;
        float dialogHeight = Gdx.graphics.getHeight() * 0.8f;
        dialog.setSize(dialogWidth, dialogHeight);

        // Set dark blue background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f)); // Dark blue color
        pixmap.fill();

        // Create a texture from the pixmap
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        // Set the texture as the dialog's background
        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        return dialog;
    }

    private void highlightSelectedButton(ImageButton selectedButton) {
        selectedButton.getImage().addAction(Actions.forever(Actions.sequence(
                Actions.color(new Color(1f, 1f, 0f, 1f), 0.5f),
                Actions.color(new Color(1f, 1f, 0f, 0.5f), 0.5f)
        )));
    }

    public void setCards(ArrayList<Card> currentDeck) {
        ArrayList<AllCards> factionCards = controller.getFactionCardsRepeated();
        ArrayList<AllCards> selectedCards = new ArrayList<>();
        for (Card card : currentDeck) {
            factionCards.remove(card.getAllCard());
            selectedCards.add(card.getAllCard());
        }
        addCardsToTable(factionCards, availableCardsTable);
        addCardsToTable(selectedCards, selectedCardsTable);
    }

    public void updateCards() {
        ArrayList<AllCards> cards = controller.getFactionCardsRepeated();
        availableCardsTable.clear();
        selectedCardsTable.clear();
        addCardsToTable(cards, availableCardsTable);
    }

    private void showDeckSelection() {
        if (controller.noFactionSelected()) {
            System.out.println("Error: No faction selected!");
            return;
        }

        Dialog dialog = createNewDialog("Select Deck");
        dialog.setWidth(Gdx.graphics.getWidth());
        dialog.setHeight(Gdx.graphics.getHeight() * 0.9f);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Left section for available cards
        availableCardsTable.top();
        availableCardsScrollPane = new ScrollPane(availableCardsTable, skin);
        availableCardsScrollPane.setScrollingDisabled(true, false);
        mainTable.add(availableCardsScrollPane).width(dialog.getWidth() * 0.4f).height(dialog.getHeight()).pad(10);

        // Middle section for stats
        statsTable.add(heroCountLabel).pad(10).row();
        statsTable.add(unitCountLabel).pad(10).row();
        statsTable.add(spellCountLabel).pad(10).row();
        mainTable.add(statsTable).width(dialog.getWidth() * 0.15f).height(dialog.getHeight()).pad(10);

        // Right section for selected cards
        selectedCardsTable.top();
        selectedCardsScrollPane = new ScrollPane(selectedCardsTable, skin);
        selectedCardsScrollPane.setScrollingDisabled(true, false);
        mainTable.add(selectedCardsScrollPane).width(dialog.getWidth() * 0.4f).height(dialog.getHeight()).pad(10);

        dialog.getContentTable().add(mainTable).pad(10);
        dialog.button("Cancel").pad(20);
        dialog.show(stage);
    }

    private void addCardsToTable(ArrayList<AllCards> cards, Table table) {
        for (AllCards card : cards) {

            String cardImagePath = card.getImageURL();
            Texture cardTexture = game.assetManager.get(cardImagePath, Texture.class);
            ImageButton cardButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(cardTexture)));

            cardButton.setUserObject(cardImagePath);

            cardButton.getImageCell().size(buttonSize, buttonSize);

            // Add hover and click effects
            cardButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    cardButton.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                    explanationLabel.setText(card.getDescription());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    cardButton.getImage().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    explanationLabel.setText("");
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Table parentTable = cardButton.getParent() instanceof Table ? (Table) cardButton.getParent() : null;

                    if (parentTable != null && parentTable.equals(availableCardsTable)) {
                        controller.selectCard(card);
                        moveCardToTable(cardButton, availableCardsTable, selectedCardsTable, buttonSize);
                    } else if (parentTable != null && parentTable.equals(selectedCardsTable)) {
                        controller.deSelectCard(card);
                        moveCardToTable(cardButton, selectedCardsTable, availableCardsTable, buttonSize);
                    }
                    updateStats();
                }
            });

            table.add(cardButton).pad(10).width(buttonSize).height(buttonSize);

            // Create a new row after every 3 cards
            if (table.getChildren().size % 3 == 0) {
                table.row();
            }
        }


    }

    private void moveCardToTable(ImageButton cardButton, Table fromTable, Table toTable, float buttonSize) {
        fromTable.removeActor(cardButton);
        toTable.add(cardButton).pad(10).width(buttonSize).height(buttonSize);

        rebuildTableGrid(fromTable, buttonSize);

        if (toTable.getChildren().size % 3 == 0) {
            toTable.row();
        }
    }

    private void rebuildTableGrid(Table table, float buttonSize) {
        Array<Actor> children = new Array<>(table.getChildren());
        table.clear();

        for (int i = 0; i < children.size; i++) {
            table.add(children.get(i)).pad(10).width(buttonSize).height(buttonSize);
            if ((i + 1) % 3 == 0) {
                table.row();
            }
        }
    }

    private void updateStats() {
        heroCountLabel.setText("Number of Hero Cards: " + controller.getHeroCount());
        unitCountLabel.setText("Number of Unit Cards: " + controller.getUnitCount());
        spellCountLabel.setText("Number of Spell Cards: " + controller.getSpellCount());
    }
}
