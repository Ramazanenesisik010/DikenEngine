package com.emirenesgames.engine.gui.background;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.gui.IBackground;

public class DownBackground implements IBackground {
	
	private int xp = 0;
	private Bitmap bitmap;
	
	public DownBackground(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void render(Bitmap bitmap) {
		for(int x = 0; x < (DikenEngine.WIDTH / 32) + 1; x++) {
			for(int y = 0; y < (DikenEngine.WIDTH / 32) + 3; y++) {
				bitmap.blendDraw(this.bitmap, x * 32, ((y * 32) + xp) - (32 * 2), 0xff1d1d1d);
			}
		}
	}

	public void tick() {
		xp+=1;
		if(xp > 32) {
			xp = 0;
		}
	}

}
