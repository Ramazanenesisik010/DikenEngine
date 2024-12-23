package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;

public class Button extends GuiObject {
	private static final long serialVersionUID = 1L;
	public String text = "";
	public int id, renderX, renderY, xa, ya;
	
	
	public Button(String text, int x, int y, int width, int height, int id) {
		super(x, y, width, height);
		this.xa = x;
		this.ya = y;
		this.renderX = xa;
		this.renderY = ya;
		this.text = text;
		this.id = id;	
	}
	
	public Bitmap render() {
		Bitmap bitmap = new Bitmap(width + 4, height + 4);
		bitmap.fill(0, 0, 0 + width, 0 + height, 0xff484848);
		
		for (int i = 0; i < width; i++) {
			bitmap.draw(Art.i.button[0][1], i, height - 12);
			bitmap.draw(Art.i.button[2][1], i, 0);
		}
		
		for (int i = 0; i < height; i++) {
			bitmap.draw(Art.i.button[1][0], 0, 0 + i);
			bitmap.draw(Art.i.button[1][1], 0 + width - 12, 0 + i);
		}
		
		bitmap.draw(Art.i.button[0][0], 0, 0 + (height - 12));
		bitmap.draw(Art.i.button[0][2], 0 + width, 0 + (height - 12));
		bitmap.draw(Art.i.button[2][0], 0, 0);
		bitmap.draw(Art.i.button[1][2], 0 + width, 0);
        
        return bitmap;
	}
}
