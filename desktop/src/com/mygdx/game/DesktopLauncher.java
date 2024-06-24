package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Main;
import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;

public class DesktopLauncher {
	public static void main (String[] arg) {

		GameManager gameManager = new GameManager(new Player(null, null, null, null) , new Player(null, null, null, null));
		Card card = new Card(AllCards.YoungBerserker);
		card.getAbility().run(gameManager, card);

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Login");
		config.setWindowedMode(2560, 1600);
		new Lwjgl3Application(new Main(), config);
	}
}
