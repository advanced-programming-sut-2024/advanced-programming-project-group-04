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
import com.mygdx.game.controller.ControllerResponse;
import com.mygdx.game.controller.MainMenuController;


public class MainMenu extends Menu {
    private final MainMenuController mainMenuController;

    Label errorLabel;

    public MainMenu(Main game) {
        super(game);
        this.mainMenuController = new MainMenuController(game);

        stage.addActor(game.assetLoader.backgroundImage);

        this.errorLabel = new Label("", game.assetLoader.labelStyle);

        // Set up table for UI layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;

        TextButton startNewGameButton = new TextButton("Start New Game", textButtonStyle);
        startNewGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ControllerResponse response = mainMenuController.startNewGame();
                errorLabel.setText(response.getError());
                errorLabel.setColor(Color.RED);
            }
        });

        TextButton setUpDeckButton = new TextButton("Set Up Your Deck", textButtonStyle);
        setUpDeckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new FactionAndLeaderMenu(game));
            }
        });

        TextButton profileButton = new TextButton("Profile", textButtonStyle);
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new ProfileMenu(game));
            }
        });

        TextButton logoutButton = new TextButton("Logout", textButtonStyle);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuController.logout();
                setScreen(new LoginMenu(game));
            }
        });

        table.add(errorLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(startNewGameButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(setUpDeckButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(profileButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(logoutButton).width(400).height(120).pad(10);
    }

}
