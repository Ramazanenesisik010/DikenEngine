package com.emirenesgames.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.emirenesgames.engine.gui.DefaultLoadingScreen;
import com.emirenesgames.engine.gui.DefaultMainMenuScreen;
import com.emirenesgames.engine.gui.LoadingScreen;
import com.emirenesgames.engine.gui.Screen;

public class GameManager {

	private Screen mainMenuScreen;
	private LoadingScreen loadingScreen;
	
	public volatile Properties config;
	public static final Properties defaultConfig = new Properties();
	public static final File defaultConfigFile = new File("./config.dat");

	/**0 = fareyi gizle ama sistem faresini gösterme
	 * <p>
	 * 1 = fareyi göster
	 * <p>
	 * 2 = sistem faresini göster**/
	public int cursorShowType = 1; 

	public GameManager() {
		defaultConfig.setProperty("fullscreen", "false");
		defaultConfig.setProperty("show_fps", "false");
		defaultConfig.setProperty("console", "false");
		defaultConfig.setProperty("sync", "true");
		defaultConfig.setProperty("legacy-crash", "false");
		defaultConfig.setProperty("debug", "false");
		defaultConfig.setProperty("lang", "tr-TR");
		defaultConfig.setProperty("antialiasing", "false");
		defaultConfig.setProperty("title", "DikenEngine " + DikenEngine.VERSION);
		
		this.config = defaultConfig;
		this.mainMenuScreen = new DefaultMainMenuScreen();
		this.loadingScreen = new DefaultLoadingScreen("Running Engine");
		
		this.loadConfig(defaultConfigFile);
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
	
	public boolean loadConfig(File configFile) {
		if(configFile == null) {
			System.err.println("File is Null!");
			return false;
		}
		try {
			DataInputStream stream = new DataInputStream(new GZIPInputStream(new FileInputStream(configFile)));
			Properties properties = new Properties();
			int size = stream.readInt();
			for(int i = 0; i < size; i++) {
				String data = stream.readUTF();
				String[] datas = data.split("=");
				properties.setProperty(datas[0], datas[1]);
			}
			this.cursorShowType = stream.readInt();
			DikenEngine.getEngine().TARGET_FPS = stream.readLong();
			stream.close();
			this.config = properties;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void saveConfig(File configFile) {
		if(configFile == null) {
			System.err.println("File is Null!");
			return;
		}
		
		try {
			DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(configFile)));
			String[] keys = config.keySet().toArray(new String[] {});
			String[] values = config.values().toArray(new String[] {});
			stream.writeInt(config.size());
			for(int i = 0; i < config.size(); i++) {
				stream.writeUTF(keys[i] + "=" + values[i]);
			}
			stream.writeInt(this.cursorShowType);
			stream.writeLong(DikenEngine.getEngine().TARGET_FPS);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean loadConfig() {
		return this.loadConfig(defaultConfigFile);
	}
	
	public void saveConfig() {
		this.saveConfig(defaultConfigFile);
	}
}
