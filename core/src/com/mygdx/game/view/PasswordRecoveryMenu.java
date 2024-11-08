package mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import mygdx.game.Main;
import mygdx.game.controller.ControllerResponse;
import mygdx.game.controller.PasswordRecoveryController;

public class PasswordRecoveryMenu extends Menu {
    private PasswordRecoveryController controller;
    private Label errorLabel;
    private TextField usernameField;

    public PasswordRecoveryMenu(Main game) {
        super(game);
        this.controller = new PasswordRecoveryController(game.getClient());

        stage.addActor(game.assetLoader.backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        errorLabel = new Label("", game.assetLoader.labelStyle);

        TextField.TextFieldStyle textFieldStyle = game.assetLoader.textFieldStyle;
        usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setAlignment(Align.center);

        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;

        TextButton confirmButton = new TextButton("Confirm", textButtonStyle);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                ControllerResponse response = controller.confirmButtonClicked(username);
                if (!response.isFailed()) {
                    showSecurityQuestionDialog();
                } else {
                    errorLabel.setText(response.getError());
                    errorLabel.setColor(Color.RED);
                }
            }
        });

        TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new LoginMenu(game));
            }
        });

        float pad = 30;
        table.add(errorLabel).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(usernameField).width(400).pad(pad);
        table.row().pad(10, 0, 10, 0);
        table.add(confirmButton).pad(10).width(400).height(120);
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).pad(10).width(400).height(120);
    }

    private void showSecurityQuestionDialog() {
        Dialog dialog = new Dialog("Security Question", game.assetLoader.skin) {
            {
                setBackground(createNewBackground());

                Label questionLabel = new Label(controller.getForgetPasswordQuestion(), game.assetLoader.labelStyle);
                TextField answerField = new TextField("", game.assetLoader.textFieldStyle);
                answerField.setMessageText("Enter your answer");

                TextButton submitButton = new TextButton("Submit", game.assetLoader.textButtonStyle);
                submitButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String answer = answerField.getText();
                        ControllerResponse response = controller.submitAnswerButtonClicked(answer);
                        if (!response.isFailed()) {
                            showNewPasswordDialog();
                        } else {
                            errorLabel.setText(response.getError());
                            errorLabel.setColor(Color.RED);
                        }
                        hide();
                    }
                });

                getContentTable().add(questionLabel).pad(20);
                getContentTable().row().pad(10, 0, 10, 0);
                getContentTable().add(answerField).width(400).pad(10);
                getContentTable().row().pad(10, 0, 10, 0);
                getContentTable().add(submitButton).width(200).height(80).pad(10);
            }
        };
        dialog.show(stage);
    }

    private void showNewPasswordDialog() {
        Dialog dialog = new Dialog("New Password", game.assetLoader.skin) {
            {
                setBackground(createNewBackground());

                Label passwordLabel = new Label("Enter your new password:", game.assetLoader.labelStyle);
                TextField passwordField = new TextField("", game.assetLoader.textFieldStyle);
                passwordField.setPasswordMode(true);
                passwordField.setPasswordCharacter('*');

                TextButton submitButton = new TextButton("Submit", game.assetLoader.textButtonStyle);
                submitButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String newPassword = passwordField.getText();
                        controller.changePassword(newPassword);
                        hide();
                        setScreen(new LoginMenu(game));
                    }
                });

                getContentTable().add(passwordLabel).pad(20);
                getContentTable().row().pad(10, 0, 10, 0);
                getContentTable().add(passwordField).width(400).pad(10);
                getContentTable().row().pad(10, 0, 10, 0);
                getContentTable().add(submitButton).width(200).height(80).pad(10);
            }
        };
        dialog.show(stage);
    }

    private Drawable createNewBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f)); //dark blue
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
}
