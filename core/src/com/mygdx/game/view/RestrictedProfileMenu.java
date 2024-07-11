package mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import mygdx.game.AssetLoader;
import mygdx.game.Main;
import mygdx.game.controller.ProfileController;
import mygdx.game.controller.RestrictedProfileController;
import mygdx.game.controller.Server;
import mygdx.game.model.Player;
import mygdx.game.model.data.PlayerProfileData;
import mygdx.game.model.data.PlayerRankData;

import java.util.ArrayList;
import java.util.Vector;

public class RestrictedProfileMenu extends Menu {
    private RestrictedProfileController controller;
    private ProfileController profileController;
    private final Skin skin;
    private final Image backgroundImage;

    private Label statisticsLabel;
    private Label matchHistoryLabel;
    private Label backLabel;
    private Label userInfoLabel;
    private Table contentTable;

    Label.LabelStyle labelStyle = game.assetLoader.labelStyle;
    TextField.TextFieldStyle textFieldStyle = game.assetLoader.textFieldStyle;

    public RestrictedProfileMenu(Main game, int playerId) {
        super(game);
        this.controller = new RestrictedProfileController(game, playerId);

        this.skin = game.assetLoader.skin;

        Texture backgroundTexture = game.assetManager.get(AssetLoader.BACKGROUND, Texture.class);
        this.backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        setupProfileMenu();

        statisticsLabel.setColor(Color.YELLOW);
    }

    private void setupProfileMenu() {
        Table tabTable = new Table();
        tabTable.align(Align.topLeft).pad(20);
        tabTable.setFillParent(true);
        stage.addActor(tabTable);

        backLabel = createTabLabel("Back to Main Menu");
        statisticsLabel = createTabLabel("Statistics");
        matchHistoryLabel = createTabLabel("Match History");
        userInfoLabel = createTabLabel("User Info");


        backLabel.addListener(new ChangeTabListener(backLabel, "back"));
        statisticsLabel.addListener(new ChangeTabListener(statisticsLabel, "statistics"));
        matchHistoryLabel.addListener(new ChangeTabListener(matchHistoryLabel, "matchHistory"));
        userInfoLabel.addListener(new ChangeTabListener(userInfoLabel, "userInfo"));


        tabTable.add(backLabel).pad(20);
        tabTable.add(statisticsLabel).pad(20);
        tabTable.add(matchHistoryLabel).pad(20);
        tabTable.add(userInfoLabel).pad(20);

        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.center();
        stage.addActor(contentTable);

        showTabContent("statistics");
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
            case "statistics":
                createStatisticsContent();
                break;
            case "matchHistory":
                contentTable.add(new Label("Match History Content", game.assetLoader.labelStyle)).pad(20);
                break;
            case "userInfo":
                createUserInfoContent();
                break;
        }
    }

    private void createStatisticsContent() {
        Table mainTable = new Table();

        ArrayList<PlayerRankData> allPlayers = profileController.getAllPlayersRankData();
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
        BitmapFont font = AssetLoader.getFontWithCustomSize(35);
        Label.LabelStyle style = new Label.LabelStyle(font, color);
        return new Label(text, style);
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
            statisticsLabel.setColor(Color.WHITE);
            matchHistoryLabel.setColor(Color.WHITE);

            label.setColor(Color.YELLOW);
            showTabContent(tabName);
        }
    }

    private void createUserInfoContent() {
        PlayerProfileData player = controller.getPlayer();
        BitmapFont font = AssetLoader.getFontWithCustomSize(40);

        Label.LabelStyle infoLabelStyle = new Label.LabelStyle();
        infoLabelStyle.font = font;
        infoLabelStyle.fontColor = Color.WHITE;

        String factionName = (player.getSelectedFaction() != null) ? player.getSelectedFaction().getName() : "No Faction";
        String leaderName = (player.getDeck().getLeader() != null) ? player.getDeck().getLeader().getName() : "No Leader";
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
