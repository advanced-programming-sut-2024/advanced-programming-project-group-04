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
import com.mygdx.game.controller.SignUpController;

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

        // Error label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label errorLabel = new Label("", labelStyle);

        // Username, email and Password fields
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.CHARTREUSE;
        textFieldStyle.background = skin.getDrawable("textfield");
        textFieldStyle.messageFontColor = Color.CYAN;

        TextField usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setAlignment(Align.center);

        TextField emailField = new TextField("", textFieldStyle);
        emailField.setMessageText("Email");
        emailField.setAlignment(Align.center);

        TextField nicknameField = new TextField("", textFieldStyle);
        nicknameField.setMessageText("Nickname");
        nicknameField.setAlignment(Align.center);

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
        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                String nickname = nicknameField.getText();
                ControllerResponse response = SignUpController.signUpButtonPressed(username, password, email, nickname);
                errorLabel.setText(response.getError());
                if (response.isFailed()) errorLabel.setColor(Color.RED);
                else errorLabel.setColor(Color.GREEN);
            }
        });

        TextButton backButton = new TextButton("Back", signUpStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new LoginMenu(game));
            }
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
        table.add(errorLabel).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(usernameField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0); // Double the row padding
        table.add(passwordField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(emailField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(nicknameField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(signUpButton).pad(pad).width(400).height(120);
        table.row().pad(20, 0, 20, 0);
        table.add(backButton).pad(pad).width(400).height(120);
    }
}
