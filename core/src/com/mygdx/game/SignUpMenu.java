package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Input;

public class SignUpMenu extends Menu {

    public SignUpMenu(Main game) {
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

        // Username, email and Password fields
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background = skin.getDrawable("textfield");

        TextField usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setAlignment(Align.center);

        TextField emailField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Email");
        usernameField.setAlignment(Align.center);

        TextField passwordField = new TextField("", textFieldStyle);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setAlignment(Align.center);

        // Sign Up button
        TextButton.TextButtonStyle signUpStyle = new TextButton.TextButtonStyle();
        signUpStyle.font = font;
        signUpStyle.fontColor = Color.WHITE;
        signUpStyle.up = skin.getDrawable("button-c");
        signUpStyle.down = skin.getDrawable("button-pressed-c");
        signUpStyle.over = skin.getDrawable("button-over-c");

        TextButton signUpButton = new TextButton("Sign Up", signUpStyle);
        signUpButton.addListener(event -> {
            if (signUpButton.isPressed()) {
                // Handle sign-up logic
            }
            return false;
        });


        // Add input listener for Enter key
        usernameField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    signUpButton.toggle(); // Trigger the sign-in button press
                }
                return true;
            }
        });

        passwordField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    signUpButton.toggle(); // Trigger the sign-in button press
                }
                return true;
            }
        });

        float pad = 40; // Double the padding
        table.add(usernameField).fillX().uniformX().pad(pad);
        table.row().pad(20, 0, 20, 0); // Double the row padding
        table.add(passwordField).fillX().uniformX().pad(pad);
        table.row().pad(20, 0, 20, 0);
        // Double width and height for buttons
        table.add(signUpButton).fillX().uniformX().pad(pad).width(400).height(120);
        table.row().pad(20, 0, 20, 0);
        // Set minimum width and height for buttons (double previous values)
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
