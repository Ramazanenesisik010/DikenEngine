package me.ramazanenescik04.diken.testgame;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.game.IGame;
import me.ramazanenescik04.diken.gui.screen.DefaultMainMenuScreen;
import me.ramazanenescik04.diken.gui.screen.Screen;

public class TestGame extends Screen implements IGame {

	public void startGame(DikenEngine engine) {
		engine.setCurrentScreen(new DefaultMainMenuScreen());
	}

	public void loadResources() {
	}

}
