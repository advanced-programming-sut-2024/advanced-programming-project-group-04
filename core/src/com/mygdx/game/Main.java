package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.controller.Client;
import com.mygdx.game.controller.ServerCommand;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.message.Message;
import com.mygdx.game.view.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Game {
    public AssetManager assetManager;
    public AssetLoader assetLoader;
    private Player loggedInPlayer;
    private Music backgroundMusic;
    private Client client;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetLoader = new AssetLoader(assetManager);
        assetLoader.loadAll();
        assetManager.finishLoading();
        assetLoader.initialize();


        // Play background music
        backgroundMusic = this.assetManager.get(AssetLoader.MUSIC, Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        this.client = new Client(this, "127.0.0.1");

        Player arman = getClient().sendToServer(ServerCommand.FETCH_USER, "arman");
        Player arvin2 = getClient().sendToServer(ServerCommand.FETCH_USER, "arvin2");
        Player matin = getClient().sendToServer(ServerCommand.FETCH_USER, "matin");
        Player mahbod = getClient().sendToServer(ServerCommand.FETCH_USER, "mahbod");
        arman.sendFriendRequest(mahbod);
        matin.sendFriendRequest(mahbod);
        arvin2.sendFriendRequest(mahbod);
        mahbod.acceptFriendRequest(arman);
        arman.sendFriendRequest(arvin2);
        arman.sendFriendRequest(matin);
        matin.acceptFriendRequest(arman);

        mahbod.sendMessage(arman, "salam");
        delay(100);
        arman.sendMessage(mahbod, "naddafhafez");
        delay(100);
        arman.sendMessage(mahbod, "hahahaha");
        delay(100);
        mahbod.sendMessage(arman, "sirk");
        delay(100);
        ArrayList<Message> chatArmanVaMahbod = mahbod.getChatWithPlayer(arman);

        // Set the initial screen
        setScreen(new LoginMenu(this));
    }

    public void setLoggedInPlayer(Player player) {
        this.loggedInPlayer = player;
    }

    public Player getLoggedInPlayer() {
        return this.loggedInPlayer;
    }

    public Client getClient() { return this.client; }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        client.closeConnection();
    }

    public void cheraBenzinTamoomShod() {
        backgroundMusic.stop();
        Music mohandesMusic = backgroundMusic = this.assetManager.get(AssetLoader.MOHANDES, Music.class);
        mohandesMusic.setLooping(true);
        mohandesMusic.play();
    }

    public void delay(int millis) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // FOR TESTING PURPOSES
        // DELETE THIS LATER
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void startGame() {
        setScreen(new GameMenu(this));
    }
}
