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
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;


public class FactionAndLeaderMenu extends Menu {

    private String selectedFaction = "";
    private String selectedLeader = "";
    private ImageButton selectedFactionButton = null;
    private ImageButton selectedLeaderButton = null;
    private Label explanationLabel = null;

    public FactionAndLeaderMenu(Main game) {
        super(game);

        // Load assets
        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);

        // Set up background
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

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

        // Add buttons to table with updated sizes
        table.add(chooseFactionButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(chooseLeaderButton).width(400).height(120).pad(10);
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
        pixmap.dispose(); // Dispose pixmap as it's no longer needed

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
        if (selectedFaction.isEmpty()) {
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
        pixmap.dispose(); // Dispose pixmap as it's no longer needed

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

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
