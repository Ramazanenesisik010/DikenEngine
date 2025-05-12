package me.ramazanenescik04.diken.game;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import me.ramazanenescik04.diken.DikenEngine;

public class GameLoader {
	private URLClassLoader loader;
	
	public IGame loadGame(URL[] urls, String classPath) {
		try {
			if (loader != null) {
				loader.close();
			}
			
			loader = new URLClassLoader(urls);
			
			Class<?> gameClass = loader.loadClass(classPath);
			
			//Obje IGame'mi kontrolü
			Object unkdownObject = gameClass.getConstructor().newInstance();
			
			if (unkdownObject instanceof IGame) {
				IGame game = (IGame) unkdownObject;
				
				return game;
			} else {
				DikenEngine.errorLog("classPath != IGame");
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void close() {
		if (loader != null)
			try {
				loader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
