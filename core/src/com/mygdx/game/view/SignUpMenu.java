package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Input;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;
import com.mygdx.game.controller.ControllerResponse;
import com.mygdx.game.controller.SignUpController;

public class SignUpMenu extends Menu {
    private final SignUpController signUpController;
    Label errorLabel;

    public SignUpMenu(Main game) {
        super(game);

        this.signUpController = new SignUpController(game.getClient());
        signUpController.setSignUpView(this);
        stage.addActor(game.assetLoader.backgroundImage);

        // Set up table for UI layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Error label
        errorLabel = new Label("", game.assetLoader.labelStyle);

        // Username, email and Password fields
        TextField.TextFieldStyle textFieldStyle = game.assetLoader.textFieldStyle;

        TextField usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setAlignment(Align.center);

        TextField passwordField = new TextField("", textFieldStyle);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setAlignment(Align.center);

        TextField emailField = new TextField("", textFieldStyle);
        emailField.setMessageText("Email");
        emailField.setAlignment(Align.center);

        TextField nicknameField = new TextField("", textFieldStyle);
        nicknameField.setMessageText("Nickname");
        nicknameField.setAlignment(Align.center);

        // Sign Up button
        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;

        TextButton signUpButton = new TextButton("Sign Up", textButtonStyle);
        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                String nickname = nicknameField.getText();
                ControllerResponse response = signUpController.signUpButtonPressed(username, password, email, nickname);
                errorLabel.setText(response.getError());
                if (response.isFailed()) errorLabel.setColor(Color.RED);
                else errorLabel.setColor(Color.GREEN);
            }
        });

        TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new LoginMenu(game));
            }
        });

        // Add input listener for Enter key
        nicknameField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    InputEvent click = new InputEvent();
                    click.setType(InputEvent.Type.touchDown);
                    signUpButton.fire(click);
                    click.setType(InputEvent.Type.touchUp);
                    signUpButton.fire(click);
                }
                return true;
            }
        });

        float pad = 30;
        table.add(errorLabel).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(usernameField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(passwordField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(emailField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(nicknameField).width(400).pad(pad);
        table.row().pad(10, 0, 10, 0);
        table.add(signUpButton).pad(pad).width(400).height(120);
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).pad(pad).width(400).height(120);
    }

    public void verifyVerificationCode(String actualCode) {
        Dialog dialog = new Dialog("Verify Email", game.assetLoader.skin);

        // Set dialog size
        float dialogWidth = Gdx.graphics.getWidth() * 0.8f;
        float dialogHeight = Gdx.graphics.getHeight() * 0.6f;
        dialog.setSize(dialogWidth, dialogHeight);

        // Set dark blue background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f)); // Dark blue color
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        Table table = new Table();
        table.setFillParent(true);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = game.assetLoader.font;
        textFieldStyle.fontColor = Color.WHITE;

        TextField verificationCodeField = new TextField("", textFieldStyle);
        verificationCodeField.setMessageText("Enter verification code");

        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;
        TextButton confirmButton = new TextButton("Confirm", textButtonStyle);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String enteredCode = verificationCodeField.getText();
                dialog.hide();
                ControllerResponse response = signUpController.verifyButtonPressed(actualCode, enteredCode);
                errorLabel.setText(response.getError());
                if (response.isFailed()) errorLabel.setColor(Color.RED);
                else errorLabel.setColor(Color.GREEN);
            }
        });

        table.add(verificationCodeField).width(400).pad(10);
        table.row();
        table.add(confirmButton).width(200).height(70).pad(10);

        dialog.getContentTable().add(table).expand().fill();
        dialog.show(stage);
        return;
    }
}
