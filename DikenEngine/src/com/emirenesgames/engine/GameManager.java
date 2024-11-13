package com.emirenesgames.engine;

import java.util.Properties;

import com.emirenesgames.engine.gui.DefaultMainMenuScreen;
import com.emirenesgames.engine.gui.Screen;

public class GameManager {

	private Screen mainMenuScreen;
	
	public Properties config;

	public GameManager() {
		this.config = new Properties();
		this.config.setProperty("fullscreen", "false");
		this.config.setProperty("show_fps", "false");
		this.config.setProperty("title", "DikenEngine " + DikenEngine.VERSION);
		this.mainMenuScreen = new DefaultMainMenuScreen();
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
}
