package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.*;

public class Main extends Game {
    public AssetManager assetManager;
    public AssetLoader assetLoader; // Add this line

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetLoader = new AssetLoader(assetManager); // Modify this line
        assetLoader.loadAll();

        // Wait until all assets are loaded
        assetManager.finishLoading();

        // Play background music
        Music backgroundMusic = this.assetManager.get(AssetLoader.MUSIC, Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // Set the initial screen
        setScreen(new GameMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
