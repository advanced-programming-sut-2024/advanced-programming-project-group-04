package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.faction.*;

import java.util.ArrayList;
import java.util.HashMap;


public class FactionAndLeaderMenu extends Menu {

    private String selectedFaction = "";
    private String selectedLeader = "";
    private ImageButton selectedFactionButton = null;
    private ImageButton selectedLeaderButton = null;
    private Label explanationLabel = null;
    private Faction faction;

    Table availableCardsTable = new Table();
    Table selectedCardsTable = new Table();
    Table statsTable = new Table();

    Label heroCountLabel;
    Label unitCountLabel;
    Label spellCountLabel;

    private HashMap<String, AllCards> cardLookupMap = new HashMap<>();

    public FactionAndLeaderMenu(Main game) {
        super(game);

        // Load assets
        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);

        // Set up background
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        faction = Player.getLoggedInPlayer().getSelectedFaction();
        if (faction == null) selectedFaction = "";
        else selectedFaction = Player.getLoggedInPlayer().getSelectedFaction().getImageURL();

        // get the font
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Gwent-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        font = generator.generateFont(parameter);
        generator.dispose();

        // Set up table for UI layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Choose Faction Button
        TextButton.TextButtonStyle chooseFactionStyle = new TextButton.TextButtonStyle();
        chooseFactionStyle.font = font;
        chooseFactionStyle.fontColor = Color.WHITE;
        chooseFactionStyle.up = skin.getDrawable("button-c");
        chooseFactionStyle.down = skin.getDrawable("button-pressed-c");
        chooseFactionStyle.over = skin.getDrawable("button-over-c");

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        explanationLabel = new Label("", skin);
        explanationLabel.setStyle(labelStyle);
        explanationLabel.setAlignment(Align.center);
        float labelWidth = 2560 - 2 * 500;
        explanationLabel.setWrap(true);
        float labelHeight = explanationLabel.getPrefHeight();

        // Set the size of the label
        explanationLabel.setSize(labelWidth, labelHeight);

        // Position at bottom middle of the screen
        explanationLabel.setPosition(500, 200);

        // Add label to stage (assuming 'stage' is your Stage object)
        stage.addActor(explanationLabel);


        TextButton chooseFactionButton = new TextButton("Choose Faction", chooseFactionStyle);
        chooseFactionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showFactionSelection();
            }
        });

        // Choose Leader Button
        TextButton.TextButtonStyle chooseLeaderStyle = new TextButton.TextButtonStyle();
        chooseLeaderStyle.font = font;
        chooseLeaderStyle.fontColor = Color.WHITE;
        chooseLeaderStyle.up = skin.getDrawable("button-c");
        chooseLeaderStyle.down = skin.getDrawable("button-pressed-c");
        chooseLeaderStyle.over = skin.getDrawable("button-over-c");

        TextButton chooseLeaderButton = new TextButton("Choose Leader", chooseLeaderStyle);
        chooseLeaderButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLeaderSelection();
            }
        });

        TextButton selectDeckButton = new TextButton("Select Deck", chooseLeaderStyle);
        selectDeckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDeckSelection();
            }
        });

        TextButton backButton = new TextButton("Back", chooseLeaderStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new MainMenu(game));
            }
        });

        // Add buttons to table with updated sizes
        table.add(chooseFactionButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(chooseLeaderButton).width(400).height(120).pad(10);
        table.row().pad(20, 0, 20, 0);
        table.add(selectDeckButton).width(400).height(120).pad(10);
        table.row().pad(20, 0, 20, 0);
        table.add(backButton).pad(40).width(400).height(120);


        heroCountLabel = new Label("Number of Hero Cards: 0", skin);
        unitCountLabel = new Label("Number of Unit Cards: 0", skin);
        spellCountLabel = new Label("Number of Spell Cards: 0", skin);
        heroCountLabel.setStyle(new Label.LabelStyle(font, Color.WHITE));
        unitCountLabel.setStyle(new Label.LabelStyle(font, Color.WHITE));
        spellCountLabel.setStyle(new Label.LabelStyle(font, Color.WHITE));


    }

    private void showFactionSelection() {
        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        Dialog dialog = new Dialog("Select Faction", skin);

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

        Table factionTable = new Table();
        factionTable.center();



        // Load faction images using AssetLoader
        AssetLoader assetLoader = game.assetLoader;
        for (String factionPath : assetLoader.getFactions()) {
            Texture factionTexture = assetLoader.getAssetManager().get(factionPath, Texture.class);
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
                    selectedFaction = factionName;
                    if (factionName.contains("skellige")) faction = new Skellige();
                    if (factionName.contains("scoiatael")) faction = new Scoiatael();
                    if (factionName.contains("realms")) faction = new NorthernRealms();
                    if (factionName.contains("nilfgaard")) faction = new Nilfgaard();
                    if (factionName.contains("monsters")) faction = new Monsters();
                    Player.getLoggedInPlayer().setFaction(faction);
                    //TODO @arman Player.getLoggedInPlayer.setFaction() ro ye joori ezafe kon;
                    highlightSelectedFaction(factionButton);
                    dialog.hide();
                    System.out.println("Selected Faction: " + selectedFaction);
                }
            });

            if (factionName.equals(selectedFaction)) {
                highlightSelectedFaction(factionButton);
            }

            factionTable.add(factionButton).pad(20);
        }

        dialog.getContentTable().add(factionTable).pad(50);
        dialog.button("Cancel").pad(20); // Update padding for Cancel button
        dialog.show(stage);
    }

    private void highlightSelectedFaction(ImageButton selectedButton) {
        // Highlight the selected button
        selectedButton.getImage().addAction(Actions.forever(Actions.sequence(
                Actions.color(new Color(1f, 1f, 0f, 1f), 0.5f),
                Actions.color(new Color(1f, 1f, 0f, 0.5f), 0.5f)
        )));
        selectedFactionButton = selectedButton;
    }

    private void showLeaderSelection() {
        if (faction == null) {
            System.out.println("Please select a faction first.");
            return;
        }

        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        Dialog dialog = new Dialog("Select Faction", skin);

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

        Table leaderTable = new Table();
        leaderTable.center();

        // Load leader images using AssetLoader
        AssetLoader assetLoader = game.assetLoader;
        for (String leaderPath : assetLoader.getLeaders(selectedFaction)) {
            Texture leaderTexture = assetLoader.getAssetManager().get(leaderPath, Texture.class);
            ImageButton leaderButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leaderTexture)));
            String leaderName = leaderPath.substring(leaderPath.lastIndexOf("/") + 1, leaderPath.lastIndexOf("."));

            float buttonSize = 450;
            leaderButton.getImageCell().size(buttonSize, buttonSize);

            // Add hover and click effects
            leaderButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    leaderButton.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                    System.out.println(leaderPath);
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
                    selectedLeader = leaderName;

                    highlightSelectedLeader(leaderButton);
                    dialog.hide();
                    System.out.println("Selected Leader: " + selectedLeader);
                }
            });

            if (leaderName.equals(selectedLeader)) {
                highlightSelectedLeader(leaderButton);
            }

            leaderTable.add(leaderButton).pad(20);
        }

        dialog.getContentTable().add(leaderTable).pad(50);
        dialog.button("Cancel").pad(20); // Update padding for Cancel button
        dialog.show(stage);
    }

    private void highlightSelectedLeader(ImageButton selectedButton) {
        // Highlight the selected button
        selectedButton.getImage().addAction(Actions.forever(Actions.sequence(
                Actions.color(new Color(1f, 1f, 0f, 1f), 0.5f),
                Actions.color(new Color(1f, 1f, 0f, 0.5f), 0.5f)
        )));
        selectedLeaderButton = selectedButton;
    }

    private void showDeckSelection() {
        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        Dialog dialog = new Dialog("Select Deck", skin);

        if (faction == null) {
            System.out.println("Error: No faction selected!");
            return;
        }

        float dialogWidth = Gdx.graphics.getWidth();
        float dialogHeight = Gdx.graphics.getHeight() * 0.8f; // 80% of the screen height
        dialog.setSize(dialogWidth, dialogHeight);
        dialog.setPosition(0, Gdx.graphics.getHeight() * 0.05f);

        // Set dark blue background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f)); // Dark blue color
        pixmap.fill();

        // Create a texture from the pixmap
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        // Set the texture as the dialog's background
        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Left section for available cards

        availableCardsTable.top();
        ScrollPane availableCardsScrollPane = new ScrollPane(availableCardsTable, skin);
        availableCardsScrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling only
        mainTable.add(availableCardsScrollPane).width(dialogWidth * 0.4f).height(dialogHeight).pad(10);

        // Middle section for stats


        statsTable.add(heroCountLabel).pad(10).row();
        statsTable.add(unitCountLabel).pad(10).row();
        statsTable.add(spellCountLabel).pad(10).row();
        mainTable.add(statsTable).width(dialogWidth * 0.15f).height(dialogHeight).pad(10);

        // Right section for selected cards

        selectedCardsTable.top();
        ScrollPane selectedCardsScrollPane = new ScrollPane(selectedCardsTable, skin);
        selectedCardsScrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling only
        mainTable.add(selectedCardsScrollPane).width(dialogWidth * 0.4f).height(dialogHeight).pad(10);

        AssetLoader assetLoader = game.assetLoader;
        ArrayList<AllCards> neutralCards = Faction.getNeutralCards();
        ArrayList<AllCards> factionCards = new ArrayList<>();

        if (faction instanceof Skellige) {
            factionCards = Skellige.getCards();
        } else if (faction instanceof Scoiatael) {
            factionCards = Scoiatael.getCards();
        } else if (faction instanceof NorthernRealms) {
            factionCards = NorthernRealms.getCards();
        } else if (faction instanceof Monsters) {
            factionCards = Monsters.getCards();
        } else if (faction instanceof Nilfgaard) {
            factionCards = Nilfgaard.getCards();
        }

        addCardsToTable(neutralCards, availableCardsTable, assetLoader);
        addCardsToTable(factionCards, availableCardsTable, assetLoader);

        dialog.getContentTable().add(mainTable).pad(10);
        dialog.button("Cancel").pad(20); // Update padding for Cancel button
        dialog.show(stage);
    }

    private void addCardsToTable(ArrayList<AllCards> cards, Table table, AssetLoader assetLoader) {
        for (AllCards card : cards) {
            cardLookupMap.put(card.getImageURL(), card);
            if (card.getNumber() > 0) {
                for (int i = 0; i < card.getNumber(); i++) {
                    String cardImagePath = card.getImageURL();
                    Texture cardTexture = assetLoader.getAssetManager().get(cardImagePath, Texture.class);
                    ImageButton cardButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(cardTexture)));

                    cardButton.setUserObject(cardImagePath);

                    float buttonSize = 300;
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
                                moveCardToTable(cardButton, availableCardsTable, selectedCardsTable, buttonSize);
                            } else if (parentTable != null && parentTable.equals(selectedCardsTable)) {
                                moveCardToTable(cardButton, selectedCardsTable, availableCardsTable, buttonSize);
                            }
                            updateStats(selectedCardsTable, heroCountLabel, unitCountLabel, spellCountLabel);
                        }
                    });

                    table.add(cardButton).pad(10).width(buttonSize).height(buttonSize);

                    // Create a new row after every 3 cards
                    if (table.getChildren().size % 3 == 0) {
                        table.row();
                    }
                }
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
        table.clearChildren();

        for (int i = 0; i < children.size; i++) {
            table.add(children.get(i)).pad(10).width(buttonSize).height(buttonSize);
            if ((i + 1) % 3 == 0) {
                table.row();
            }
        }
    }

    private void updateStats(Table selectedCardsTable, Label heroCountLabel, Label unitCountLabel, Label spellCountLabel) {
        int heroCount = 0;
        int unitCount = 0;
        int spellCount = 0;

        for (Actor actor : selectedCardsTable.getChildren()) {
            if (actor instanceof ImageButton) {
                ImageButton cardButton = (ImageButton) actor;
                String cardName = (String) cardButton.getUserObject();
                AllCards card = cardLookupMap.get(cardName);
                System.out.println(cardName);
                if (card != null) {
                    if (card.isHero()) {
                        heroCount++;
                    }
                    if (card.isUnitCard()) {
                        unitCount++;
                    } else {
                        spellCount++;
                    }
                } else {
                    System.out.println("Error: Card is null for button: " + cardButton);
                }
            }
        }

        heroCountLabel.setText("Number of Hero Cards: " + heroCount);
        unitCountLabel.setText("Number of Unit Cards: " + unitCount);
        spellCountLabel.setText("Number of Spell Cards: " + spellCount);
    }

}
