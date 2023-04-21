package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.*;

public class PongGame extends Game {
	// Screen Size
	public static final int SCREEN_WIDTH = 720;
	public static final int SCREEN_HEIGHT = 480;

	// Constants for Screens
	public final static int MAIN_MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int PAUSE = 2;
	public final static int GAME_PLAY = 3;
	private int previousScreen;

	// Screens
	private PreferencesScreen preferencesScreen;
	private MainMenuScreen mainMenuScreen;
	private PauseMenuScreen pauseMenuScreen;
	private GameplayScreen gameplayScreen;

	// preferences
	private AppPreferences preferences;

	// Asset Manager
	public PongAssetManager assets;

	@Override
	public void create () {
		// Create our active preferences file & update just in case
		preferences = new AppPreferences();
		updateActivePreferences();

		// Create our asset manager
		assets = new PongAssetManager();

		// Create the mainscreen and make it the current screen
		mainMenuScreen = new MainMenuScreen(this);
		previousScreen = MAIN_MENU;
		setScreen(mainMenuScreen);

	}

	@Override
	public void dispose () {
		// dispose of all screens and all of our assets
		gameplayScreen.dispose();
		mainMenuScreen.dispose();
		pauseMenuScreen.dispose();
		preferencesScreen.dispose();
		assets.manager.dispose();

	}

	// Get preferences, to be used by child screen
	public AppPreferences getPreferences(){
		return this.preferences;
	}

	// Get previous, to be used for return to the proper menu
	public int getPreviousScreen() {
		return previousScreen;
	}

	// Tells the gameplay screen to implement the current preferences
	public void updateActivePreferences(){
		if (gameplayScreen != null) {
			gameplayScreen.updatePreferences(
					preferences.getMusicVolume(),
					preferences.isMusicEnabled(),
					preferences.getSoundVolume(),
					preferences.isSoundEffectsEnabled()
			);
		}
	}



	public void changeScreen(int screen, int currentScreen){
		previousScreen = currentScreen; // set previous to the screen that called this function

		switch(screen){
			case MAIN_MENU:
				// Make a new screen in case it hasn't been.
				if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this);
				// switch the screen
				this.setScreen(mainMenuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case PAUSE:
				if(pauseMenuScreen == null) pauseMenuScreen = new PauseMenuScreen(this);
				this.setScreen(pauseMenuScreen);
				break;
			case GAME_PLAY:
				// UNSURE ON THIS ONE following a tutorial
				if(gameplayScreen == null) gameplayScreen = new GameplayScreen(this);
				this.setScreen(gameplayScreen);
				break;
		}
	}

	public void clearGame() {
		gameplayScreen.dispose();
		gameplayScreen = null;
	}

}
