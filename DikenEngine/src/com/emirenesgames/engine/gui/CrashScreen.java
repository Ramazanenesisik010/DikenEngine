package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;

public class CrashScreen extends Screen {
	
	private Exception e;
	
	public CrashScreen(Exception e) {
		this.e = e;
	}

	public void render(Bitmap screen) {
		screen.clear(0xffff0000);
		Text.renderCenter(e.toString(), screen, (DikenEngine.WIDTH / 2), 20);
		Text.renderCenter("Error!", screen, (DikenEngine.WIDTH / 2), 2);
	}
	
	
	
}
