package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;

public class Text {
	public static BitmapAABB[] fontColors = new BitmapAABB[Short.MAX_VALUE];
	
	public static void init() {
		BitmapAABB aabb = new BitmapAABB();
		aabb.btp = Art.i.font;
		fontColors[0] = aabb;
		createFont(Art.loadAndCut("/font.png", 6, 8, Art.CLASS_RESOURCE_LOAD), 0xffffffff, 0xff242424, 1);
	}
	
	public static final String chars = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
			"abcdefghijklmnopqrstuvwxyz_               " + //
			"0123456789+-=*:;ÖÅÄå                      " + //
			"";

	public static void render(String text, Bitmap bitmap, int x, int y) {
		for (int i = 0; i < text.length(); i++) {
			int ch = chars.indexOf(text.charAt(i));
			if (ch < 0) ch = 104;

			int xx = ch % 42;
			int yy = ch / 42;
			bitmap.draw(fontColors[1].btp[xx][yy], (x + i * 6) + 1, y + 1);
			bitmap.draw(fontColors[0].btp[xx][yy], x + i * 6, y);
		}
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y) {
		int x1 = x - (string.length() * 6 / 2);
		
		render(string,bitmap,x1,y);
	}
	
	public static void createFont(Bitmap[][] font, int oldColor, int newColor, int id) {
		Bitmap[][] recolored = Art.recolor(font, oldColor, newColor);
		if(fontColors[id] != null) {
			return;
		}
		
		BitmapAABB aabb = new BitmapAABB();
		aabb.btp = recolored;
		fontColors[id] = aabb;
	}
	
	private static class BitmapAABB {
		public Bitmap[][] btp;
	}
}
