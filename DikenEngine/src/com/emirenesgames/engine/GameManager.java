package com.emirenesgames.engine;

import java.util.Properties;

import com.emirenesgames.engine.gui.DefaultLoadingScreen;
import com.emirenesgames.engine.gui.DefaultMainMenuScreen;
import com.emirenesgames.engine.gui.LoadingScreen;
import com.emirenesgames.engine.gui.Screen;

public class GameManager {

	private Screen mainMenuScreen;
	private LoadingScreen loadingScreen;
	
	public Properties config;

	/**0 = fareyi gizle ama sistem faresini gösterme
	 * 1 = fareyi göster
	 * 2= sistem faresini göster**/
	public int cursorShowType = 1; 

	public GameManager() {
		this.config = new Properties();
		this.config.setProperty("fullscreen", "false");
		this.config.setProperty("show_fps", "false");
		this.config.setProperty("console", "false");
		this.config.setProperty("sync", "true");
		this.config.setProperty("title", "DikenEngine " + DikenEngine.VERSION);
		this.mainMenuScreen = new DefaultMainMenuScreen();
		this.loadingScreen = new DefaultLoadingScreen("Running Engine");
	}
	
	public void setMainMenu(Screen screen) {
		this.mainMenuScreen = screen;
	}
	
	public boolean mainMenuIsOpen() {
		return DikenEngine.getEngine().currentScreen.equals(mainMenuScreen);
	}
	
	public void openMainMenu() {
		DikenEngine.getEngine().setCurrentScreen(mainMenuScreen);;
	}

	public void setLoadingScreen(LoadingScreen screen) {
		this.loadingScreen = screen;
	}
	
	public boolean loadingScreenIsOpen() {
		return DikenEngine.getEngine().currentScreen.equals(loadingScreen);
	}
	
	public void openLoadingScreen() {
		DikenEngine.getEngine().setCurrentScreen(loadingScreen);;
	}
}
