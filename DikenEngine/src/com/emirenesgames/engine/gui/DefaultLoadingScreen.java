package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.gui.background.DownBackground;
import com.emirenesgames.engine.gui.background.StaticBackground;

public class DefaultLoadingScreen extends LoadingScreen {
	
	public String loadingText;

	public DefaultLoadingScreen(String loadingText) {
		this.loadingText = loadingText;
	}

	public void render(Bitmap screen) {
		super.render(screen);
		Text.render(loadingText, screen, 2, 2);
		Text.render("Powered By:", screen, (DikenEngine.WIDTH / 2) - (Art.i.icon_x16.w / 2), (DikenEngine.HEIGHT / 2) - ((Art.i.icon_x16.h / 2) * 2));
		Text.render("DikenEngine", screen, (DikenEngine.WIDTH / 2) - (Art.i.icon_x16.w / 2), (DikenEngine.HEIGHT / 2) - ((Art.i.icon_x16.h / 2) * 1));
		screen.draw(Art.i.icon_x16, ((DikenEngine.WIDTH / 2) - (Art.i.icon_x16.w / 2)) - 33, (DikenEngine.HEIGHT / 2) - 33);
	}

	public void openScreen() {
		this.setBackground(new DownBackground(Art.i.bgd_tiles[4][0]));
	}
	
	

}
