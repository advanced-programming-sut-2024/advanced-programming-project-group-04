package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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

    Skin skin;

    float buttonSize = 300;

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

    ScrollPane availableCardsScrollPane;
    ScrollPane selectedCardsScrollPane;

    private final HashMap<String, AllCards> cardLookupMap = new HashMap<>();

    public FactionAndLeaderMenu(Main game) {
        super(game);

        skin = game.assetLoader.skin;
        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        faction = game.getLoggedInPlayer().getSelectedFaction();
        if (faction == null) selectedFaction = "";
        else selectedFaction = game.getLoggedInPlayer().getSelectedFaction().getImageURL();

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
                    selectedFaction = factionName;
                    if (factionName.contains("skellige")) faction = new Skellige();
                    if (factionName.contains("scoiatael")) faction = new Scoiatael();
                    if (factionName.contains("realms")) faction = new NorthernRealms();
                    if (factionName.contains("nilfgaard")) faction = new Nilfgaard();
                    if (factionName.contains("monsters")) faction = new Monsters();
                    game.getLoggedInPlayer().setFaction(faction);
                    //TODO @arman Player.getLoggedInPlayer.setFaction() ro ye joori ezafe kon;
                    highlightSelectedButton(factionButton);
                    selectedFactionButton = factionButton;
                    updateAvailableCards();
                    dialog.hide();
                    System.out.println("Selected Faction: " + selectedFaction);
                }
            });

            if (factionName.equals(selectedFaction)) {
                highlightSelectedButton(factionButton);
                selectedFactionButton = factionButton;
            }

            factionTable.add(factionButton).pad(20);
        }

        dialog.getContentTable().add(factionTable).pad(50);
        dialog.button("Cancel").pad(20); // Update padding for Cancel button
        dialog.show(stage);
    }

    private void showLeaderSelection() {
        if (faction == null) {
            System.out.println("Please select a faction first.");
            return;
        }

        Dialog dialog = createNewDialog("Select Leader");

        Table leaderTable = new Table();
        leaderTable.center();

        // Load leader images using AssetLoader
        AssetLoader assetLoader = game.assetLoader;
        for (String leaderPath : assetLoader.getLeaders(selectedFaction)) {
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

                    highlightSelectedButton(leaderButton);
                    selectedLeaderButton = leaderButton;
                    dialog.hide();
                    System.out.println("Selected Leader: " + selectedLeader);
                }
            });

            if (leaderName.equals(selectedLeader)) {
                highlightSelectedButton(leaderButton);
                selectedLeaderButton = leaderButton;
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
        // Highlight the selected button
        selectedButton.getImage().addAction(Actions.forever(Actions.sequence(
                Actions.color(new Color(1f, 1f, 0f, 1f), 0.5f),
                Actions.color(new Color(1f, 1f, 0f, 0.5f), 0.5f)
        )));
    }

    private void updateAvailableCards() {
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

        availableCardsTable.clear();
        selectedCardsTable.clear();
        addCardsToTable(factionCards, availableCardsTable);
        addCardsToTable(neutralCards, availableCardsTable);
    }

    private void showDeckSelection() {
        if (faction == null) {
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
            cardLookupMap.put(card.getImageURL(), card);
            if (card.getNumber() > 0) {
                for (int i = 0; i < card.getNumber(); i++) {
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
                                moveCardToTable(cardButton, availableCardsTable, selectedCardsTable, buttonSize);
                            } else if (parentTable != null && parentTable.equals(selectedCardsTable)) {
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
//        table.clearChildren();
        table.clear();

        for (int i = 0; i < children.size; i++) {
            table.add(children.get(i)).pad(10).width(buttonSize).height(buttonSize);
            if ((i + 1) % 3 == 0) {
                table.row();
            }
        }
    }

    private void updateStats() {
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
