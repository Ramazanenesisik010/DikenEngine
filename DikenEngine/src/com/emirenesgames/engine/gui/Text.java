package com.emirenesgames.engine.gui;

import java.awt.Color;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.tools.ChatAllowedCharacters;

public class Text {

	public static void render(String text, Bitmap bitmap, int x, int y, int color, UniFont font) {
		for (int i = 0; i < text.length(); i++) {
			int ch = font.charTypes.indexOf(text.charAt(i));
			if (ch < 0) continue;
			if (ch > font.charBitmaps.length) continue;
			
			Bitmap btp = font.charBitmaps[ch];
			
			Color color1 = new Color(color);
			
			int red = color1.getRed();
			int green = color1.getGreen();
			int blue = color1.getBlue();
			
			red -= 222;
			green -= 222;
			blue -= 222;
			
			Color darkColor = new Color(red, green, blue);
			
			bitmap.blendDraw(btp, (x + i * btp.w) + 1, y + 1, darkColor.getRGB());
			bitmap.blendDraw(btp, x + i * btp.w, y, color);
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
