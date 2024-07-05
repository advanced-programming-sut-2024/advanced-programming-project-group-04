package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.message.Message;
import com.mygdx.game.view.*;

import java.util.ArrayList;

public class Main extends Game {
    public AssetManager assetManager;
    public AssetLoader assetLoader;
    private Player loggedInPlayer;
    private Music backgroundMusic;

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
        // backgroundMusic.play();

        Player arman = new Player("arman", "123", "a@b.com", "tahmasb");
        Player arvin2 = new Player("arvin2", "123", "A@B.com", "Gay");
        Player matin = new Player("matin", "123", "a@b.com", "tahmasb fan");
        Player mahbod = new Player("mahbod", "123", "M@K.com", "Khalvati");
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
<<<<<<< Updated upstream
        setScreen(new LoginMenu(this));

=======
        setScreen(new GameMenu(this));
>>>>>>> Stashed changes
    }

    public void setLoggedInPlayer(Player player) {
        this.loggedInPlayer = player;
    }

    public Player getLoggedInPlayer() {
        return this.loggedInPlayer;
    }

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

    public void delay(int millis) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // FOR TESTING PURPOSES
        // DELETE THIS LATER
    }
}
