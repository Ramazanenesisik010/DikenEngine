package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;

public class Text {

	public static void render(String text, Bitmap bitmap, int x, int y, int color, UniFont font) {
		Bitmap[] chars = UniFont.getBitmapChars(text, font);
		int w = 0;
		for (int i = 0; i < chars.length; i++) {		
			Bitmap btp = chars[i];
			if (btp == UniFont.getBitmapChar('§', font) && i + 6 < text.length())
            {	
                String colorCode = text.substring(i + 1, i + 7);
                
                color = (int) Long.parseLong(colorCode, 16);
                
                i += 6;
                continue;          
            }
			
			int darkColor = 0, var6;
			var6 = darkColor & -16777216;
			darkColor = (color & 16579836) >> 2;
		    darkColor += var6;
			
			bitmap.blendDraw(btp, (x + w) + 1, y + 1, darkColor);
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
	
	public static int stringBitmapWidth(String text, UniFont font) {
		Bitmap[] chars = UniFont.getBitmapChars(text, font);
		int w = 0;
		for (int i = 0; i < chars.length; i++) {
			Bitmap btp = chars[i];
			w += ((btp.w) + 1);
		}
		
		return w;
	}
	
	public static int stringBitmapAverageHeight(String text, UniFont font) {
		Bitmap[] chars = UniFont.getBitmapChars(text, font);
		int h = 0;
		int ah = 0;
		for (int i = 0; i < chars.length; i++) {
			Bitmap btp = chars[i];
			ah += ((btp.h) + 1);
		}
		
		if(chars.length <= 0) {
			return 0;
		}
		
		h = ah / chars.length;
		
		return h;
	}
}
