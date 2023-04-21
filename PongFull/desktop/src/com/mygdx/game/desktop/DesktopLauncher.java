package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Bodies.PongBody;
import com.mygdx.game.PongGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = PongGame.SCREEN_HEIGHT;
		config.width = PongGame.SCREEN_WIDTH;
		config.title = "Pong - Full Version";
		new LwjglApplication(new PongGame(), config);
	}
}
