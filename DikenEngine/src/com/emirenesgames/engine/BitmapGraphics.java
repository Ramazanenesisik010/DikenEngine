package com.emirenesgames.engine;

import java.awt.image.BufferedImage;

public class BitmapGraphics extends Bitmap {
	
	public BitmapGraphics(int w, int h) {
	   super(w, h);
    }

	public BitmapGraphics(int w, int h, int[] pixels) {
	   super(w, h, pixels);
	}

	public BitmapGraphics(BufferedImage img) {
	   super(img);
	}
	
	public void drawRect(int x, int y, int w, int h,int Color) {
		this.box(x, y, x + w, y + h, Color);
	}

}
