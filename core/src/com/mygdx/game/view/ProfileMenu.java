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
    private final BitmapFont font;
    private final Image backgroundImage;

    private Label changeCredentialsLabel;
    private Label statisticsLabel;
    private Label matchHistoryLabel;
    private Table contentTable;

    public ProfileMenu(Main game) {
        super(game);

        // Load assets
        this.skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);

        // Set up background
        this.backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Load the font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Gwent-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 48;
        this.font = generator.generateFont(parameter);
        generator.dispose();

        // Set up the profile menu UI
        setupProfileMenu();

        // Set the initial tab to "Change Credentials"
        changeCredentialsLabel.setColor(Color.YELLOW);
    }

    private void setupProfileMenu() {
        Table tabTable = new Table();
        tabTable.align(Align.topLeft).pad(20);
        tabTable.setFillParent(true);
        stage.addActor(tabTable);

        // Create tab labels
        changeCredentialsLabel = createTabLabel("Change Credentials", Color.WHITE);
        statisticsLabel = createTabLabel("Statistics", Color.WHITE);
        matchHistoryLabel = createTabLabel("Match History", Color.WHITE);

        // Add listeners to switch tabs
        changeCredentialsLabel.addListener(new ChangeTabListener(changeCredentialsLabel, "changeCredentials"));
        statisticsLabel.addListener(new ChangeTabListener(statisticsLabel, "statistics"));
        matchHistoryLabel.addListener(new ChangeTabListener(matchHistoryLabel, "matchHistory"));

        // Add labels to the table
        tabTable.add(changeCredentialsLabel).pad(20);
        tabTable.add(statisticsLabel).pad(20);
        tabTable.add(matchHistoryLabel).pad(20);

        // Content table to display the tab content
        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.center();
        stage.addActor(contentTable);

        // Initially show the "Change Credentials" tab content
        showTabContent("changeCredentials");
    }

    private Label createTabLabel(String text, Color color) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = color;
        return new Label(text, labelStyle);
    }

    private void showTabContent(String tabName) {
        contentTable.clear();
        switch (tabName) {
            case "changeCredentials":
                // Add Change Credentials content
                createChangeCredentialsContent();
                break;
            case "statistics":
                // Add Statistics content
                contentTable.add(new Label("Statistics Content", new Label.LabelStyle(font, Color.WHITE))).pad(20);
                break;
            case "matchHistory":
                // Add Match History content
                contentTable.add(new Label("Match History Content", new Label.LabelStyle(font, Color.WHITE))).pad(20);
                break;
        }
    }

    private void createChangeCredentialsContent() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
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

        contentTable.add(changeUsernameButton).width(400).height(120).pad(10).center();
        contentTable.row().pad(10, 0, 10, 0);
        contentTable.add(changePasswordButton).width(400).height(120).pad(10).center();
        contentTable.row().pad(10, 0, 10, 0);
        contentTable.add(changeEmailButton).width(400).height(120).pad(10).center();
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
                    // Dark blue background
                    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                    pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f));
                    pixmap.fill();
                    Texture texture = new Texture(pixmap);
                    pixmap.dispose();
                    setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

                    // Custom label style
                    Label.LabelStyle labelStyle = new Label.LabelStyle();
                    labelStyle.font = font;
                    labelStyle.fontColor = Color.WHITE;

                    // Create pixmap for cursor and selection if not present in skin
                    Pixmap cursorPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                    cursorPixmap.setColor(Color.WHITE);
                    cursorPixmap.fill();
                    Texture cursorTexture = new Texture(cursorPixmap);
                    cursorPixmap.dispose();

                    Pixmap selectionPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                    selectionPixmap.setColor(new Color(0.5f, 0.5f, 1f, 0.5f));
                    selectionPixmap.fill();
                    Texture selectionTexture = new Texture(selectionPixmap);
                    selectionPixmap.dispose();

                    // Custom text field style
                    TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
                    textFieldStyle.font = font;
                    textFieldStyle.fontColor = Color.WHITE;
                    textFieldStyle.background = skin.getDrawable("textfield");
                    textFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(cursorTexture));
                    textFieldStyle.selection = new TextureRegionDrawable(new TextureRegion(selectionTexture));

                    getContentTable().add(new Label("New " + fieldType + ":", labelStyle)).pad(20);

                    TextField textField = new TextField("", textFieldStyle);
                    if (fieldType.equals("Password")) {
                        textField.setPasswordMode(true);
                        textField.setPasswordCharacter('*');
                    }
                    getContentTable().add(textField).width(300).pad(20);

                    // Custom button style
                    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
                    buttonStyle.font = font;
                    buttonStyle.fontColor = Color.WHITE;
                    buttonStyle.up = skin.getDrawable("button-c");
                    buttonStyle.down = skin.getDrawable("button-pressed-c");
                    buttonStyle.over = skin.getDrawable("button-over-c");

                    button(new TextButton("Confirm", buttonStyle), true);
                }


                @Override
                protected void result(Object object) {
                    if ((Boolean) object) {
                        // Handle the confirmation of the new credentials here
                    }
                    hide();
                }
            };
            dialog.show(stage);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
}
