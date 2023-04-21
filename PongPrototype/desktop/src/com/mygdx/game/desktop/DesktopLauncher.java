package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Pong;



public class DesktopLauncher {
	public static void main (String[] arg) {
		// is is the configuration class
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// we'll set the screen size to a static for now.
		config.width = 1000;
		config.height = 600;

		//Now create an Application, using your game, and your configuration
		new LwjglApplication(new Pong(), config);
	}
}
