package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Main;
import com.mygdx.game.model.faction.Skellige;
import com.mygdx.game.model.leader.skellige.KingBran;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Login");
		config.setWindowedMode(2560, 1600);
		new Lwjgl3Application(new Main(), config);
	}
}
