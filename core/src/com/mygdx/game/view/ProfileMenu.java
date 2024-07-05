package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Main;
import com.mygdx.game.AssetLoader;

public class ProfileMenu extends Menu {
    private final Skin skin;
    private final Image backgroundImage;

    private Label changeCredentialsLabel;
    private Label statisticsLabel;
    private Label matchHistoryLabel;
    private Label backLabel;
    private Table contentTable;

    Label.LabelStyle labelStyle = game.assetLoader.labelStyle;
    TextField.TextFieldStyle textFieldStyle = game.assetLoader.textFieldStyle;

    public ProfileMenu(Main game) {
        super(game);

        this.skin = game.assetLoader.skin;

        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);
        this.backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        setupProfileMenu();

        changeCredentialsLabel.setColor(Color.YELLOW);
    }

    private void setupProfileMenu() {
        Table tabTable = new Table();
        tabTable.align(Align.topLeft).pad(20);
        tabTable.setFillParent(true);
        stage.addActor(tabTable);

        backLabel = createTabLabel("Back to Main Menu");
        changeCredentialsLabel = createTabLabel("Change Credentials");
        statisticsLabel = createTabLabel("Statistics");
        matchHistoryLabel = createTabLabel("Match History");

        backLabel.addListener(new ChangeTabListener(backLabel, "back"));
        changeCredentialsLabel.addListener(new ChangeTabListener(changeCredentialsLabel, "changeCredentials"));
        statisticsLabel.addListener(new ChangeTabListener(statisticsLabel, "statistics"));
        matchHistoryLabel.addListener(new ChangeTabListener(matchHistoryLabel, "matchHistory"));

        tabTable.add(backLabel).pad(20);
        tabTable.add(changeCredentialsLabel).pad(20);
        tabTable.add(statisticsLabel).pad(20);
        tabTable.add(matchHistoryLabel).pad(20);

        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.center();
        stage.addActor(contentTable);

        showTabContent("changeCredentials");
    }

    private Label createTabLabel(String text) {
        return new Label(text, game.assetLoader.labelStyle);
    }

    private void showTabContent(String tabName) {
        contentTable.clear();
        switch (tabName) {
            case "back":
                setScreen(new MainMenu(game));
                break;
            case "changeCredentials":
                createChangeCredentialsContent();
                break;
            case "statistics":
                // Add Statistics content
                contentTable.add(new Label("Statistics Content", game.assetLoader.labelStyle)).pad(20);
                break;
            case "matchHistory":
                // Add Match History content
                contentTable.add(new Label("Match History Content", game.assetLoader.labelStyle)).pad(20);
                break;
        }
    }

    private void createChangeCredentialsContent() {
        BitmapFont font2 = AssetLoader.getFontWithCustomSize(40);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font2;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.up = skin.getDrawable("button-c");
        buttonStyle.down = skin.getDrawable("button-pressed-c");
        buttonStyle.over = skin.getDrawable("button-over-c");

        TextButton changeUsernameButton = new TextButton("Change Username", buttonStyle);
        changeUsernameButton.addListener(new ChangeCredentialsClickListener("Username"));

        TextButton changePasswordButton = new TextButton("Change Password", buttonStyle);
        changePasswordButton.addListener(new ChangeCredentialsClickListener("Password"));

        TextButton changeEmailButton = new TextButton("Change Email", buttonStyle);
        changeEmailButton.addListener(new ChangeCredentialsClickListener("Email"));

        TextButton setUpForgetPasswordQuestionButton = new TextButton("Password Recovery", buttonStyle);
        setUpForgetPasswordQuestionButton.addListener(new PasswordRecoveryClickListener());

        String twoFAButtonString = (game.getLoggedInPlayer().getTwoFAEnabled()) ? "Disable 2FA" : "Enable 2FA";
        TextButton twoFAButton = new TextButton(twoFAButtonString, buttonStyle);
        twoFAButton.addListener(new Toggle2FAClickListener(twoFAButton));

        contentTable.add(changeUsernameButton).width(400).height(120).pad(10).center();
        contentTable.row().pad(10, 0, 10, 0);
        contentTable.add(changePasswordButton).width(400).height(120).pad(10).center();
        contentTable.row().pad(10, 0, 10, 0);
        contentTable.add(changeEmailButton).width(400).height(120).pad(10).center();
        contentTable.row().pad(10, 0, 10, 0);
        contentTable.add(setUpForgetPasswordQuestionButton).width(400).height(120).pad(10).center();
        contentTable.row().pad(10, 0, 10, 0);
        contentTable.add(twoFAButton).width(400).height(120).pad(10).center();

    }

    private class ChangeTabListener extends ClickListener {
        private final Label label;
        private final String tabName;

        public ChangeTabListener(Label label, String tabName) {
            this.label = label;
            this.tabName = tabName;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            changeCredentialsLabel.setColor(Color.WHITE);
            statisticsLabel.setColor(Color.WHITE);
            matchHistoryLabel.setColor(Color.WHITE);

            label.setColor(Color.YELLOW);
            showTabContent(tabName);
        }
    }

    private class ChangeCredentialsClickListener extends ClickListener {
        private final String fieldType;

        public ChangeCredentialsClickListener(String fieldType) {
            this.fieldType = fieldType;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Dialog dialog = new Dialog("Change " + fieldType, skin) {
                {
                    setBackground(createNewBackground());

                    getContentTable().add(new Label("New " + fieldType + ":", labelStyle)).pad(20);

                    TextField textField = new TextField("", textFieldStyle);
                    if (fieldType.equals("Password")) {
                        textField.setPasswordMode(true);
                        textField.setPasswordCharacter('*');
                    }
                    getContentTable().add(textField).width(300).pad(20);

                    button(new TextButton("Confirm", game.assetLoader.textButtonStyle), true);
                }


                @Override
                protected void result(Object object) {
                    if ((Boolean) object) {
                        // TODO Handle the confirmation of the new credentials here
                    }
                    hide();
                }
            };
            dialog.show(stage);
        }
    }

    private class PasswordRecoveryClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Dialog dialog = new Dialog("Password Recovery", skin) {
                {
                    setBackground(createNewBackground());

                    getContentTable().add(new Label("Question:", labelStyle)).pad(20);

                    TextField questionField = new TextField("", textFieldStyle);
                    getContentTable().add(questionField).width(300).pad(20);
                    getContentTable().row();

                    getContentTable().add(new Label("Answer:", labelStyle)).pad(20);

                    TextField answerField = new TextField("", textFieldStyle);
                    answerField.setPasswordMode(true);
                    answerField.setPasswordCharacter('*');
                    getContentTable().add(answerField).width(300).pad(20);

                    button(new TextButton("Confirm", game.assetLoader.textButtonStyle), true);
                }

                @Override
                protected void result(Object object) {
                    if ((Boolean) object) {
                        // TODO Handle the confirmation of the new credentials here
                    }
                    hide();
                }
            };
            dialog.show(stage);
        }
    }

    private class Toggle2FAClickListener extends ClickListener {
        TextButton button;
        public Toggle2FAClickListener(TextButton button) {
            this.button = button;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.getLoggedInPlayer().toggleTwoFA();
            String twoFAButtonString = (game.getLoggedInPlayer().getTwoFAEnabled()) ? "Disable 2FA" : "Enable 2FA";
            // TODO @arman move this part to controller
            button.setText(twoFAButtonString);
        }
    }

    private TextureRegionDrawable createNewBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f));
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
