package mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import mygdx.game.AssetLoader;
import mygdx.game.Main;
import mygdx.game.controller.MainMenuController;
import mygdx.game.controller.commands.ServerCommand;
import mygdx.game.model.Player;
import mygdx.game.model.message.Message;

import java.util.ArrayList;
import java.util.TimerTask;

public class MainMenu extends Menu {
    private final MainMenuController mainMenuController;

    private Label errorLabel;
    private ImageButton friendsButton;
    private ImageButton friendRequestsButton;
    private Window friendsWindow;
    private Window friendRequestsWindow;
    private Timer timer;

    public MainMenu(Main game) {
        super(game);
        this.mainMenuController = new MainMenuController(game);

        stage.addActor(game.assetLoader.backgroundImage);

        this.errorLabel = new Label("", game.assetLoader.labelStyle);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton.TextButtonStyle textButtonStyle = game.assetLoader.textButtonStyle;

        TextButton startNewGameButton = new TextButton("Start New Game", textButtonStyle);
        startNewGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ControllerResponse response = mainMenuController.startNewGame();
//                errorLabel.setText(response.getError());
//                errorLabel.setColor(Color.RED);
                setScreen(new StartGameMenu(game));
            }
        });

        TextButton setUpDeckButton = new TextButton("Set Up Your Deck", textButtonStyle);
        setUpDeckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new FactionAndLeaderMenu(game));
            }
        });

        TextButton profileButton = new TextButton("Profile", textButtonStyle);
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new ProfileMenu(game));
            }
        });

        TextButton logoutButton = new TextButton("Logout", textButtonStyle);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuController.logout();
                setScreen(new LoginMenu(game));
            }
        });

        table.add(errorLabel).padTop(10).padBottom(10);
        table.row().pad(10, 0, 10, 0);
        table.add(startNewGameButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(setUpDeckButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(profileButton).width(400).height(120).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add(logoutButton).width(400).height(120).pad(10);

        // Load friends and friend requests buttons
        Texture friendsTexture = game.assetManager.get(AssetLoader.FRIENDS, Texture.class);
        Texture friendRequestsTexture = game.assetManager.get(AssetLoader.FRIENDREQUESTS, Texture.class);
        friendsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(friendsTexture)));
        friendRequestsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(friendRequestsTexture)));

        friendsButton.setScale(1);
        friendRequestsButton.setScale(1);

        friendsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleFriendsWindow();
            }
        });

        friendRequestsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleFriendRequestsWindow();
            }
        });

        HorizontalGroup buttonGroup = new HorizontalGroup();
        buttonGroup.addActor(friendsButton);
        buttonGroup.addActor(friendRequestsButton);
        buttonGroup.space(10);

        friendsButton.setSize(160, 160);
        friendRequestsButton.setSize(160, 160);

        Group group = new Group();
        group.addActor(buttonGroup);
        group.setPosition(50, 50);

        stage.addActor(group);

        createFriendsWindow();
        createFriendRequestsWindow();
    }

    private void createFriendsWindow() {
        Texture tabsTexture = game.assetManager.get(AssetLoader.TABS, Texture.class);
        friendsWindow = new Window("Friends", game.assetLoader.skin);
        initializePopUpScreen(tabsTexture, friendsWindow);
        stage.addActor(friendsWindow);
    }

    private void createFriendRequestsWindow() {
        Texture tabsTexture = game.assetManager.get(AssetLoader.TABS, Texture.class);
        friendRequestsWindow = new Window("Friend Requests", game.assetLoader.skin);
        initializePopUpScreen(tabsTexture, friendRequestsWindow);
        stage.addActor(friendRequestsWindow);
    }

    private void initializePopUpScreen(Texture tabsTexture, Window friendsWindow) {
        friendsWindow.setSize(600, 1200);
        friendsWindow.setPosition(100, (float) Gdx.graphics.getHeight() / 2 - friendsWindow.getHeight() / 2); // Centered on the left
        friendsWindow.setVisible(false);
        friendsWindow.setBackground(new TextureRegionDrawable(new TextureRegion(tabsTexture)));
    }

    private void toggleFriendsWindow() {
        boolean visible = friendsWindow.isVisible();
        friendsWindow.setVisible(!visible);
        if (friendsWindow.isVisible()) {
            loadFriendsList();
        }
    }

    private void toggleFriendRequestsWindow() {
        boolean visible = friendRequestsWindow.isVisible();
        friendRequestsWindow.setVisible(!visible);
        if (friendRequestsWindow.isVisible()) {
            loadFriendRequestsList();
        }
    }

    private void loadFriendsList() {
        friendsWindow.clear();
        if (game.getLoggedInPlayer() == null) return;
        ArrayList<Player> friends = game.getLoggedInPlayer().getFriends();
        float buttonWidth = friendsWindow.getWidth() * 0.50f;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0f, 0.5f, 0.5f, 1f)); // blue
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        Drawable blueBackground = new TextureRegionDrawable(new TextureRegion(texture));

        if (friends == null) return;

        for (Player friend : friends) {
            Table friendEntry = new Table();
            friendEntry.setBackground(blueBackground);
            boolean isOnline = game.getClient().sendToServer(ServerCommand.IS_ONLINE, friend.getUsername());
            Texture statusTexture = game.assetManager.get(isOnline ? AssetLoader.ONLINE : AssetLoader.OFFLINE, Texture.class);
            Image statusImage = new Image(statusTexture);

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = AssetLoader.getFontWithCustomSize(38);

            // Create the friendLabel as a button instead of a label to keep the chat functionality
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = AssetLoader.getFontWithCustomSize(38);
            textButtonStyle.fontColor = Color.WHITE;
            textButtonStyle.up = game.assetLoader.skin.getDrawable("button-c");
            textButtonStyle.down = game.assetLoader.skin.getDrawable("button-pressed-c");
            textButtonStyle.over = game.assetLoader.skin.getDrawable("button-over-c");

            TextButton friendButton = new TextButton(friend.getUsername(), textButtonStyle);
            friendButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showMessageDialog(game.getLoggedInPlayer(), friend);
                }
            });

            friendEntry.add(friendButton).padRight(10).width(buttonWidth).height(60);
            friendEntry.add(statusImage).size(30, 30).padRight(10);
            friendsWindow.add(friendEntry).pad(5);
            friendsWindow.row();

            TextButton viewProfileButton = new TextButton("View Profile", textButtonStyle);
            viewProfileButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new RestrictedProfileMenu(game, friend));
                }
            });
            friendsWindow.add(viewProfileButton).pad(5).width(buttonWidth).height(60);
            friendsWindow.row();

            TextButton challengeButton = new TextButton("Challenge", textButtonStyle);
            challengeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mainMenuController.challengeFriend(friend.getUsername());
                }
            });
            friendsWindow.add(challengeButton).pad(5).width(buttonWidth).height(60);
            friendsWindow.row();
        }
    }


    private void startFriendsListUpdater() {
        this.timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                loadFriendsList();
            }
        };
        timer.scheduleTask(task, 0, 5);
    }

    private void stopUpdater() {
        timer.stop();
    }

    private void loadFriendRequestsList() {
        friendRequestsWindow.clear();
        ArrayList<Player> incomingRequests = game.getLoggedInPlayer().getIncomingFriendRequests();
        float buttonWidth = friendRequestsWindow.getWidth() * 0.5f;
        if (incomingRequests == null) return;
        for (Player request : incomingRequests) {
            TextButton usernameButton = new TextButton(request.getUsername(), game.assetLoader.textButtonStyle);
            usernameButton.getLabel().setAlignment(Align.center);

            Table requestTable = new Table();
            requestTable.add(usernameButton).width(buttonWidth).padLeft(110).padRight(110).row();

            Table buttonTable = new Table();
            TextButton rejectButton = new TextButton("Reject", game.assetLoader.textButtonStyle);
            TextButton acceptButton = new TextButton("Accept", game.assetLoader.textButtonStyle);

            acceptButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle accept button click
                }
            });

            rejectButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle reject button click
                }
            });

            buttonTable.add(rejectButton).width(buttonWidth * 0.55f).padLeft(110).padRight(-8);
            buttonTable.add(acceptButton).width(buttonWidth * 0.55f).padLeft(-8).padRight(110);

            requestTable.add(buttonTable).padTop(-10);

            friendRequestsWindow.add(requestTable).padTop(-10);
            friendRequestsWindow.row();

        }
        createFriendRequestInputField(friendRequestsWindow);
    }

    private void showMessageDialog(Player loggedInPlayer, Player friend) {
        Dialog messageDialog = new Dialog("Messages with " + friend.getUsername(), game.assetLoader.skin);
        // Set dark blue background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.2f, 1f)); // Dark blue color
        pixmap.fill();

        // Create a texture from the pixmap
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        messageDialog.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));
        messageDialog.setSize(800, 600);
        messageDialog.setPosition((float) Gdx.graphics.getWidth() / 2 - messageDialog.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - messageDialog.getHeight() / 2);

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = new TextureRegionDrawable(new TextureRegion(texture));

        Table messageTable = new Table();
        ArrayList<Message> messages = loggedInPlayer.getChatWithPlayer(friend);
        for (Message message : messages) {
            Label messageLabel = new Label(message.getContent(), game.assetLoader.labelStyle);
            messageLabel.setWrap(true);
            messageLabel.setAlignment(message.getSender().equals(loggedInPlayer) ? Align.left : Align.right);

            Cell<Label> cell = messageTable.add(messageLabel).width(700);
            if (message.getSender().equals(loggedInPlayer)) {
                cell.left();
            } else {
                cell.right();
            }
            messageTable.row();
        }

        ScrollPane scrollPane = new ScrollPane(messageTable, scrollPaneStyle);
        messageDialog.getContentTable().add(scrollPane).width(760).height(400).pad(20).row();

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = game.assetLoader.font;
        textFieldStyle.fontColor = Color.WHITE;

        TextField messageField = new TextField("", textFieldStyle);
        messageField.setMessageText("Type your message...");

        TextButton sendButton = new TextButton("Send", game.assetLoader.textButtonStyle);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String messageText = messageField.getText();
                if (!messageText.isEmpty()) {
                    // Code to send the message
                }
            }
        });

        Table messageInputTable = new Table();
        messageInputTable.add(messageField).width(600).padRight(10);
        messageInputTable.add(sendButton).width(100);

        messageDialog.getContentTable().add(messageInputTable).pad(10);

        messageDialog.button("Close");
        messageDialog.show(stage);
    }

    private void createFriendRequestInputField(Window window) {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = game.assetLoader.font;
        textFieldStyle.fontColor = Color.BLACK;

        TextField friendNameField = new TextField("", textFieldStyle);
        friendNameField.setMessageText("Enter friend's username");
        friendNameField.setWidth(160);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        Skin skin = game.assetLoader.skin;
        textButtonStyle.font = game.assetLoader.font;
        textButtonStyle.fontColor = Color.CYAN;
        textButtonStyle.up = skin.getDrawable("button-c");
        textButtonStyle.down = skin.getDrawable("button-pressed-c");
        textButtonStyle.over = skin.getDrawable("button-over-c");

        TextButton sendButton = new TextButton("Send", textButtonStyle);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String friendName = friendNameField.getText();
                if (!friendName.isEmpty()) {
                    // Send friend request
                }
            }
        });

        sendButton.setSize(120, 80);

        Table inputTable = new Table();
        inputTable.add(friendNameField).width(300).pad(10);
        inputTable.row();
        inputTable.add(sendButton).width(120).height(80).pad(10);
        inputTable.row();

        window.add(inputTable).expandX().bottom().pad(10);
    }

    @Override
    public void show() {
        startFriendsListUpdater();
    }

    @Override
    public void dispose() {
        super.dispose();
        stopUpdater();
    }

}
