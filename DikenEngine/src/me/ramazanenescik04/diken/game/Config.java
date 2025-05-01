package me.ramazanenescik04.diken.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import me.ramazanenescik04.diken.DikenEngine;

public class Config {
	
	public volatile Properties config;
	public static final Properties defaultConfig = new Properties();
	public static final File defaultConfigFile = new File("./config.dat");

	public Config() {
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
		this.loadConfig(defaultConfigFile);
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
			DikenEngine.TARGET_FPS = (int) stream.readInt();
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
			stream.writeInt(DikenEngine.TARGET_FPS);
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
