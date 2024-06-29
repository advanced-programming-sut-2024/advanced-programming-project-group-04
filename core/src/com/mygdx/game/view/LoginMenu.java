package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Input;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;
import com.mygdx.game.controller.ControllerResponse;
import com.mygdx.game.controller.LoginController;
import com.mygdx.game.model.Player;

public class LoginMenu extends Menu {

    public LoginMenu(Main game) {
        super(game);

        Player.loadAllPlayers();

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

        // Error Label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label errorLabel = new Label("", labelStyle);

        // Username and Password fields
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.CHARTREUSE;
        textFieldStyle.background = skin.getDrawable("textfield");
        textFieldStyle.messageFontColor = Color.CYAN;

        TextField usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setAlignment(Align.center);

        TextField passwordField = new TextField("", textFieldStyle);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setAlignment(Align.center);

        // Sign in button
        TextButton.TextButtonStyle signInStyle = new TextButton.TextButtonStyle();
        signInStyle.font = font;
        signInStyle.fontColor = Color.WHITE;
        signInStyle.up = skin.getDrawable("button-c");
        signInStyle.down = skin.getDrawable("button-pressed-c");
        signInStyle.over = skin.getDrawable("button-over-c");

        TextButton signInButton = new TextButton("Sign In", signInStyle);
        signInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                ControllerResponse response = LoginController.signInButtonClicked(username, password);

                if (!response.isFailed()) {
                    setScreen(new MainMenu(game));
                    Player.loginPlayer(Player.findPlayerByUsername(username));
                }
                else {
                    errorLabel.setText(response.getError());
                    errorLabel.setColor(Color.RED);
                }
            }
        });

        TextButton.TextButtonStyle createAccountStyle = new TextButton.TextButtonStyle();
        createAccountStyle.font = font;
        createAccountStyle.fontColor = Color.WHITE;
        createAccountStyle.up = skin.getDrawable("button-c");
        createAccountStyle.down = skin.getDrawable("button-pressed-c");
        createAccountStyle.over = skin.getDrawable("button-over-c");

        TextButton createAccountButton = new TextButton("Create Account", createAccountStyle);
        createAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new SignUpMenu(game));
            }
        });

        // Add input listener for Enter key
        usernameField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    signInButton.toggle(); // Trigger the sign-in button press
                }
                return true;
            }
        });

        passwordField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    signInButton.toggle(); // Trigger the sign-in button press
                }
                return true;
            }
        });

        float pad = 40; // Double the padding
        table.add(errorLabel).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(usernameField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(passwordField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(signInButton).pad(pad).width(400).height(120);
        table.row().pad(20, 0, 20, 0);
        table.add(createAccountButton).pad(pad).width(400).height(120);
    }
}
