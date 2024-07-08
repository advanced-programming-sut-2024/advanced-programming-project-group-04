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
import mygdx.game.controller.ServerCommand;
import mygdx.game.model.Player;

public class PasswordRecoveryMenu extends Menu {
    private Label errorLabel;
    private Player player;
    private TextField usernameField;

    public PasswordRecoveryMenu(Main game) {
        super(game);

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
                player = game.getClient().sendToServer(ServerCommand.FETCH_USER, username);
                if (player != null) {
                    showSecurityQuestionDialog(player);
                } else {
                    errorLabel.setText("User not found!");
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

    private void showSecurityQuestionDialog(Player player) {
        Dialog dialog = new Dialog("Security Question", game.assetLoader.skin) {
            {
                setBackground(createNewBackground());

                Label questionLabel = new Label(player.getForgetPasswordQuestion(), game.assetLoader.labelStyle);
                TextField answerField = new TextField("", game.assetLoader.textFieldStyle);
                answerField.setMessageText("Enter your answer");

                TextButton submitButton = new TextButton("Submit", game.assetLoader.textButtonStyle);
                submitButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String answer = answerField.getText();
                        if (player.validateAnswerToQuestion(answer)) {
                            showNewPasswordDialog();
                        } else {
                            errorLabel.setText("Incorrect answer!");
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
                        // TODO @Arman make it the new password in controller(or maybe here there is not much going on in the controller)
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
