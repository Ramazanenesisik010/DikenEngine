package com.emirenesgames.engine.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.Language;
import com.emirenesgames.engine.gui.background.DownBackground;
import com.emirenesgames.engine.tools.SoundTest;

public class DefaultMainMenuScreen extends Screen {
	
	public void render(Bitmap screen) {
		super.render(screen);
		screen.draw(Art.i.icon_x16, 10, (100 - 32) - 10);
		Text.render("DikenEngine", screen, (10 + 32) + 2, (100 - 32) - 10);
		Text.render(DikenEngine.VERSION, screen, (10 + 32) + 2, ((100 - 32) - 10) + 9);
	}   

	public void actionListener(int id) {
		if(id == 0) {
			engine.setCurrentScreen(new PerformaceTestScreen(this));
		}
		if(id == 1) {
			engine.setCurrentScreen(new ConsoleScreen(this));
		}
		if(id == 2) {
			engine.gManager.saveConfig();
			
			if (engine.engineWindow != null)
				engine.engineWindow.dispose();
			
			System.exit(0);
		}
        if(id == 4) {
			engine.setCurrentScreen(new SoundTest(this));
		}
		if(id == 3) {
			try {
				if(Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(new URI("https://github.com/Ramazanenesisik010/DikenEngine"));
				}
			} catch (IOException e) {
			} catch (URISyntaxException e) {
			}
		}
		if(id == 5) {
			engine.setCurrentScreen(new DES(this));
		}
	}

	public void openScreen() {
		Language lang = Language.i;
		
		this.setBackground(new DownBackground(Art.i.bgd_tiles[0][0]));
		this.buttons.clear();
		Button performaceButton = new Button(lang.languageValue("dmainmenu.performancetest"), 10, 100, 200, 15, 0);
		this.buttons.add(performaceButton);
		this.buttons.add(new Button(lang.languageValue("dmainmenu.setting"), 10, 100 + (2 * 20), 200, 15, 5));
		this.buttons.add(new Button(lang.languageValue("dmainmenu.exit"), 10, 100 + (3 * 20), 200, 15, 2));
		this.buttons.add(new Button(lang.languageValue("dmainmenu.soundtest"), 10, 100 + (1 * 20), 200, 15, 4));
		this.buttons.add(new Button("Github", 10, DikenEngine.HEIGHT - (1 * 20), 200, 15, 3));
	}
}
