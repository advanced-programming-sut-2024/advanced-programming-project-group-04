package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.controller.Client;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.*;

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
//        backgroundMusic = this.assetManager.get(AssetLoader.MUSIC, Music.class);
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();

        this.client = new Client();

        // Set the initial screen
        setScreen(new LoginMenu(this));
    }

    public void setLoggedInPlayer(Player player) { this.loggedInPlayer = player;}

    public Player getLoggedInPlayer() { return this.loggedInPlayer; }

    public Client getClient() { return this.client; }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public void cheraBenzinTamoomShod() {
        backgroundMusic.stop();
        Music mohandesMusic = backgroundMusic = this.assetManager.get(AssetLoader.MOHANDES, Music.class);
        mohandesMusic.setLooping(true);
        mohandesMusic.play();
    }
}
