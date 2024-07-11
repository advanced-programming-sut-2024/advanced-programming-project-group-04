package mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import mygdx.game.AssetLoader;
import mygdx.game.Main;
import mygdx.game.controller.ProfileController;
import mygdx.game.model.data.PlayerRankData;

import java.util.ArrayList;

public class ProfileMenu extends Menu {
    private ProfileController controller;
    private final Skin skin;
    private final Image backgroundImage;

    private Label changeCredentialsLabel;
    private Label statisticsLabel;
    private Label matchHistoryLabel;
    private Label backLabel;
    private Label userInfoLabel;
    private Table contentTable;


    Label.LabelStyle labelStyle = game.assetLoader.labelStyle;
    TextField.TextFieldStyle textFieldStyle = game.assetLoader.textFieldStyle;

    public ProfileMenu(Main game) {
        super(game);
        this.controller = new ProfileController(game.getClient());

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
        userInfoLabel = createTabLabel("User Info");

        backLabel.addListener(new ChangeTabListener(backLabel, "back"));
        changeCredentialsLabel.addListener(new ChangeTabListener(changeCredentialsLabel, "changeCredentials"));
        statisticsLabel.addListener(new ChangeTabListener(statisticsLabel, "statistics"));
        matchHistoryLabel.addListener(new ChangeTabListener(matchHistoryLabel, "matchHistory"));
        userInfoLabel.addListener(new ChangeTabListener(userInfoLabel, "userInfo"));

        tabTable.add(backLabel).pad(20);
        tabTable.add(changeCredentialsLabel).pad(20);
        tabTable.add(statisticsLabel).pad(20);
        tabTable.add(matchHistoryLabel).pad(20);
        tabTable.add(userInfoLabel).pad(20);

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
                createStatisticsContent();
                // contentTable.add(new Label("Statistics Content", game.assetLoader.labelStyle)).pad(20);
                break;
            case "matchHistory":
                // Add Match History content
                contentTable.add(new Label("Match History Content", game.assetLoader.labelStyle)).pad(20);
                break;
            case "userInfo":
                createUserInfoContent();
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

    private void createStatisticsContent() {
        Table mainTable = new Table();

        ArrayList<PlayerRankData> allPlayers = controller.getAllPlayersRankData();
//        allPlayers.get(0).addLP(353);
//        allPlayers.get(1).addLP(712);
//        allPlayers.get(2).addLP(241);
//        allPlayers.get(3).addLP(115);
//        allPlayers.get(4).addLP(78);

        allPlayers.sort((p1, p2) -> Integer.compare(p2.getLP(), p1.getLP()));

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.5f, 0.7f, 1f, 1f)); // Light blue color
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        Drawable lightBlueBackground = new TextureRegionDrawable(new TextureRegion(texture));

        int currentRank = 1;
        int previousLP = -1;

        for (int i = 0; i < allPlayers.size(); i++) {
            PlayerRankData player = allPlayers.get(i);

            if (player.getLP() != previousLP) {
                currentRank = i + 1;
                previousLP = player.getLP();
            }

            Table playerTable = new Table();
            playerTable.setBackground(lightBlueBackground);
            playerTable.setSize(1300, 200);

            Label rankLabel = getLabelFromString(currentRank + ".", Color.BLACK);
            Label usernameLabel = getLabelFromString(player.getUsername(), Color.BLACK);
            Table leftSection = new Table();
            leftSection.add(rankLabel).padRight(10);
            leftSection.add(usernameLabel).expandX().left();

            Label winsLabel = getLabelFromString("Wins: " + player.getWinCount(), Color.GREEN);
            Label lossesLabel = getLabelFromString("Losses: " + player.getLossCount(), Color.RED);
            Label drawsLabel = getLabelFromString("Draws: " + player.getDrawCount(), Color.GRAY);
            Table middleSection = new Table();
            middleSection.add(winsLabel).padRight(15);
            middleSection.add(drawsLabel).padRight(15);
            middleSection.add(lossesLabel);

            String rankIconURL = player.getRank().getImageURL();
            Texture rankTexture = game.assetManager.get(rankIconURL, Texture.class);
            Image rankImage = new Image(rankTexture);
            rankImage.setSize(150, 150);
            Label rankIconLabel = getLabelFromString(player.getRank().toString(), Color.BLACK);
            Label lpLabel = getLabelFromString(player.getLP() + " LP", Color.BLACK);

            Table rightSection = new Table();
            rightSection.add(rankImage).size(150, 150).row();
            rightSection.add(rankIconLabel).padTop(5).row();
            rightSection.add(lpLabel).padTop(5);

            playerTable.add(leftSection).expandX().left().pad(10);
            playerTable.add(middleSection).expandX().center().pad(10);
            playerTable.add(rightSection).expandX().right().pad(10);

            mainTable.add(playerTable).fillX().pad(10);
            mainTable.row();
        }

        ScrollPane scrollPane = new ScrollPane(mainTable);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setSize(1300, 1400);
        scrollPane.setPosition((stage.getWidth() - 1300) / 2, (stage.getHeight() - 1400) / 2); // Center the scroll pane

        contentTable.addActor(scrollPane);
    }

    private Label getLabelFromString(String text, Color color) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.assetLoader.font;
        labelStyle.fontColor = color;
        return new Label(text, labelStyle);
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
        private TextField textField;

        public ChangeCredentialsClickListener(String fieldType) {
            this.fieldType = fieldType;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Dialog dialog = new Dialog("Change " + fieldType, skin) {
                {
                    setBackground(createNewBackground());

                    getContentTable().add(new Label("New " + fieldType + ":", labelStyle)).pad(20);

                    textField = new TextField("", textFieldStyle);
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
                        String input = textField.getText();
                        controller.changeCredentials(fieldType, input);
                    }
                    hide();
                }
            };
            dialog.show(stage);
        }
    }

    private class PasswordRecoveryClickListener extends ClickListener {
        private TextField questionField;
        private TextField answerField;

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Dialog dialog = new Dialog("Password Recovery", skin) {
                {
                    setBackground(createNewBackground());

                    getContentTable().add(new Label("Question:", labelStyle)).pad(20);

                    questionField = new TextField("", textFieldStyle);
                    getContentTable().add(questionField).width(300).pad(20);
                    getContentTable().row();

                    getContentTable().add(new Label("Answer:", labelStyle)).pad(20);

                    answerField = new TextField("", textFieldStyle);
                    answerField.setPasswordMode(true);
                    answerField.setPasswordCharacter('*');
                    getContentTable().add(answerField).width(300).pad(20);

                    button(new TextButton("Confirm", game.assetLoader.textButtonStyle), true);
                }

                @Override
                protected void result(Object object) {
                    if ((Boolean) object) {
                        String question = questionField.getText();
                        String answer = answerField.getText();
                        controller.setQuestion(question, answer);
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
            controller.toggleTwoFA();
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

    private void createUserInfoContent() {
        Player player = game.getLoggedInPlayer();
        BitmapFont font = AssetLoader.getFontWithCustomSize(40);

        Label.LabelStyle infoLabelStyle = new Label.LabelStyle();
        infoLabelStyle.font = font;
        infoLabelStyle.fontColor = Color.WHITE;



        String factionName;
        String leaderName;
        factionName = (player.getSelectedFaction() != null) ? player.getSelectedFaction().getName() : "No Faction";
        leaderName = (player.getDeck() == null || player.getDeck().getLeader() != null) ? player.getDeck().getLeader().getName() : "No Leader";

        int totalCards = player.getDeck().getCards().size();
        int unitCards = player.getDeck().getNumberOfUnits();
        int spellCards = player.getDeck().getNumberOfSpecialCards();
        int heroCards = player.getDeck().getNumberOfHeroCards();

        Label usernameLabel = new Label("Username: " + player.getUsername(), infoLabelStyle);
        Label nicknameLabel = new Label("Nickname: " + player.getNickname(), infoLabelStyle);
        Label factionLabel = new Label("Faction: " + factionName, infoLabelStyle);
        Label leaderLabel = new Label("Leader: " + leaderName, infoLabelStyle);
        Label totalCardsLabel = new Label("Total Cards: " + totalCards, infoLabelStyle);
        Label unitCardsLabel = new Label("Unit Cards: " + unitCards, infoLabelStyle);
        Label spellCardsLabel = new Label("Spell Cards: " + spellCards, infoLabelStyle);
        Label heroCardsLabel = new Label("Hero Cards: " + heroCards, infoLabelStyle);

        contentTable.add(usernameLabel).pad(10).center();
        contentTable.row();
        contentTable.add(nicknameLabel).pad(10).center();
        contentTable.row();
        contentTable.add(factionLabel).pad(10).center();
        contentTable.row();
        contentTable.add(leaderLabel).pad(10).center();
        contentTable.row();
        contentTable.add(totalCardsLabel).pad(10).center();
        contentTable.row();
        contentTable.add(unitCardsLabel).pad(10).center();
        contentTable.row();
        contentTable.add(spellCardsLabel).pad(10).center();
        contentTable.row();
        contentTable.add(heroCardsLabel).pad(10).center();
    }
}
