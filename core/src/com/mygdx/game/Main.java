package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.*;

public class Main extends Game {
    public AssetManager assetManager;
    public AssetLoader assetLoader;
    private Player loggedInPlayer;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetLoader = new AssetLoader(assetManager);
        assetLoader.loadAll();
        assetManager.finishLoading();
        assetLoader.initialize();

        // Play background music
        Music backgroundMusic = this.assetManager.get(AssetLoader.MUSIC, Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // Set the initial screen
        setScreen(new GameMenu(this));
    }

    public void setLoggedInPlayer(Player player) { this.loggedInPlayer = player;}

    public Player getLoggedInPlayer() { return this.loggedInPlayer; }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
