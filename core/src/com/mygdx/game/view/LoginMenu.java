package mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import mygdx.game.AssetLoader;
import mygdx.game.Main;
import mygdx.game.controller.ControllerResponse;
import mygdx.game.controller.LoginController;

public class LoginMenu extends Menu {
    private final LoginController loginController;

    TextField usernameField, passwordField;
    Label errorLabel;

    public LoginMenu(Main game) {
        super(game);
        this.loginController = new LoginController(game);
        loginController.setLoginMenu(this);

        // Set up background
        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Set up table for UI layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Error Label
        errorLabel = new Label("", game.assetLoader.labelStyle);

        // Username and Password fields
        TextField.TextFieldStyle textFieldStyle = game.assetLoader.textFieldStyle;

        usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setAlignment(Align.center);

        passwordField = new TextField("", textFieldStyle);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setAlignment(Align.center);

        // Sign in button
        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;

        TextButton signInButton = new TextButton("Sign In", textButtonStyle);
        signInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                signInButtonPressed();
            }
        });
        signInButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                signInButtonPressed();
            }
        });

        TextButton createAccountButton = new TextButton("Create Account", textButtonStyle);
        createAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new SignUpMenu(game));
            }
        });


        TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = AssetLoader.getFontWithCustomSize(44);
        textButtonStyle2.fontColor = Color.WHITE;
        textButtonStyle2.up = game.assetLoader.skin.getDrawable("button-c");
        textButtonStyle2.down = game.assetLoader.skin.getDrawable("button-pressed-c");
        textButtonStyle2.over = game.assetLoader.skin.getDrawable("button-over-c");

        TextButton forgetPassword = new TextButton("Forgot password?", textButtonStyle2);
        forgetPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new PasswordRecoveryMenu(game));
            }
        });


        // Add input listener for Enter key
        usernameField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    InputEvent click = new InputEvent();
                    click.setType(InputEvent.Type.touchDown);
                    signInButton.fire(click);
                    click.setType(InputEvent.Type.touchUp);
                    signInButton.fire(click);
                }
                return true;
            }
        });

        passwordField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    InputEvent click = new InputEvent();
                    click.setType(InputEvent.Type.touchDown);
                    signInButton.fire(click);
                    click.setType(InputEvent.Type.touchUp);
                    signInButton.fire(click);
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
        table.add(signInButton).pad(10).width(400).height(120);
        table.row().pad(20, 0, 20, 0);
        table.add(createAccountButton).pad(10).width(400).height(120);
        table.row().pad(20, 0, 20, 0);
        table.add(forgetPassword).pad(10).width(400).height(120);
    }

    public void signInButtonPressed() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ControllerResponse response = loginController.signInButtonClicked(username, password);

        if (response.isFailed()) {
            errorLabel.setText(response.getError());
            errorLabel.setColor(Color.RED);
        } else {
            setScreen(new MainMenu(game));
        }
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
                ControllerResponse response = loginController.verifyButtonPressed(actualCode, enteredCode);
                errorLabel.setText(response.getError());
                if (response.isFailed()) errorLabel.setColor(Color.RED);
                else {
                    errorLabel.setColor(Color.GREEN);
                    setScreen(new MainMenu(game));
                }
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
