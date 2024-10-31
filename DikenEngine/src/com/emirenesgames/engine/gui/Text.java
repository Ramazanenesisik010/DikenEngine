package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;

public class Text {

	public static void render(String text, Bitmap bitmap, int x, int y, int color, UniFont font) {
		Bitmap[] chars = UniFont.getBitmapChars(text, font);
		int w = 0;
		for (int i = 0; i < chars.length; i++) {
			Bitmap btp = chars[i];
			bitmap.blendDraw(btp, (x + w) + 1, y + 1, color & 0xff2a2a2a);
			bitmap.blendDraw(btp, x + w, y, color);
			w += ((btp.w) + 1);
		}
	}
	
	public static void render(String text, Bitmap bitmap, int x, int y, int color) {
		render(text, bitmap, x, y, color, DikenEngine.getEngine().defaultFont);
	}
	
	public static void render(String text, Bitmap bitmap, int x, int y, UniFont font) {
		render(text, bitmap, x, y, 0xffffffff, font);
	}
	
	public static void render(String text, Bitmap bitmap, int x, int y) {
		render(text, bitmap, x, y, 0xffffffff, DikenEngine.getEngine().defaultFont);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y) {
		renderCenter(string, bitmap, x, y, 0xffffffff, DikenEngine.getEngine().defaultFont);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y, int color) {
		renderCenter(string, bitmap, x, y, color, DikenEngine.getEngine().defaultFont);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y, UniFont font) {
		renderCenter(string, bitmap, x, y, 0xffffffff, font);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y, int color, UniFont font) {
		int x1 = x - (string.length() * 6 / 2);
		
		render(string, bitmap,x1, y, color, font);
	}
}
