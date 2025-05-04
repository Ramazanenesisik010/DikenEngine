package me.ramazanenescik04.diken.game;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.screen.DefaultMainMenuScreen;

public class TestGame implements IGame {

	public void startGame(DikenEngine engine) {
		engine.setCurrentScreen(new DefaultMainMenuScreen());
	}

	public void loadResources() {
	}

}
