package com.emirenesgames.engine.gui.background;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.gui.IBackground;

public class StaticBackground implements IBackground {
	
	private Bitmap bitmap;
	
	public StaticBackground(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void render(Bitmap bitmap) {
		for(int x = 0; x < (DikenEngine.WIDTH / 32) + 1; x++) {
			for(int y = 0; y < (DikenEngine.WIDTH / 32) + 1; y++) {
				bitmap.blendDraw(this.bitmap, x * 32, y * 32, 0xff1d1d1d);
			}
		}
	}

	public void tick() {
	}

}
