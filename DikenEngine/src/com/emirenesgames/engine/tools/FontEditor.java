package com.emirenesgames.engine.tools;

import java.awt.event.*;
import java.util.*;

import com.emirenesgames.engine.*;

public class FontEditor implements GameRunner {
	
	private DikenEngine engine;
	
	private Bitmap bitmap_font;
	
	private int x, y, xStart = 0, yStart = 0, scale = 3;
	private boolean select = false;
	
	private List<FontChar> fontCharList = new ArrayList<FontChar>();

	public FontEditor() {
		this.engine = DikenEngine.getEngine();
	}

	public void render(Bitmap bitmap) {
		if (bitmap_font != null) {
			bitmap_font.setPixel(xStart, yStart, 0xffffffff);
			for (int i = 0; i < fontCharList.size(); i++) {
				FontChar fontChar = fontCharList.get(i);
				
				bitmap_font.box(fontChar.x, fontChar.y, fontChar.x + fontChar.w, fontChar.y + fontChar.h, 0xff00ff00);
			}
			
			bitmap.draw(bitmap_font, x, y);
		}
		
	}

	public void tick() {
		if (engine.input.keysDown[KeyEvent.VK_UP]) {
			y++;
		}
        if (engine.input.keysDown[KeyEvent.VK_DOWN]) {
        	y--;
		}
        if (engine.input.keysDown[KeyEvent.VK_RIGHT]) {
        	x--;
        }
        if (engine.input.keysDown[KeyEvent.VK_LEFT]) {
        	x++;
        }
        
        if (engine.input.mb0 && !select) {
        	engine.input.mb0 = false;
        	this.select  = true;
        	this.xStart = engine.mouse.x - x;
        	this.yStart = engine.mouse.y - y;
        	
        }
        
        if (engine.input.mb0 && select) {
        	engine.input.mb0 = false;
        	this.select = false;
        	int tmpX = xStart;
        	int tmpY = yStart;
        	
        	this.xStart = engine.mouse.x - x;
        	this.yStart = engine.mouse.y - y;
        	
        	FontChar fontChar = new FontChar();
        	
        	System.out.println("w: " + (xStart - tmpX));
        	System.out.println("h: " + (yStart - tmpY));
        	
        	fontChar.x = tmpX;
        	fontChar.y = tmpY;
        	fontChar.w = xStart - tmpX;
        	fontChar.h = yStart - tmpY;
        	
        	fontCharList.add(fontChar);
        	System.out.println("Added Font");
        	
        	
        }
        
		bitmap_font = Art.load("C:/Users/Ramazanenescik04/git/DikenEngine/DikenEngine/res/fonts/default_font.png", Art.NORMAL_RESOURCE_LOAD);
	}
	@SuppressWarnings("unused")
	private static class FontChar {
		public int x, y, w, h;
		
		private char fontChar;

		
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}

		public char getFontChar() {
			return fontChar;
		}

		public void setFontChar(char fontChar) {
			this.fontChar = fontChar;
		}
	}
	
	public static void main(String[] args) {
		DikenEngine.initEngine(320, 240, 2, "DikenEngine Font Editor");
		FontEditor e = new FontEditor();
		DikenEngine.addGameRunner(e);
	}
}
