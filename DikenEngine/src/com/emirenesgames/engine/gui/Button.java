package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;

public class Button extends Hitbox {
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
	
	public void render(Bitmap bitmap) {
		bitmap.fill(renderX, renderY, renderX + width, renderY + height, 0xff848484);
		
		for (int i = 0; i < width + 1; i++) {
			bitmap.draw(Art.i.button[0][1], renderX + i, renderY + height - 12);
			bitmap.draw(Art.i.button[2][1], renderX + i, renderY);
		}
		
		for (int i = 0; i < height + 1; i++) {
			bitmap.draw(Art.i.button[1][0], renderX, renderY + i);
			bitmap.draw(Art.i.button[1][1], renderX + width - 12, renderY + i);
		}
		
		bitmap.draw(Art.i.button[0][0], renderX, renderY + (height - 12));
		bitmap.draw(Art.i.button[0][2], renderX + width, renderY + (height - 12));
		bitmap.draw(Art.i.button[2][0], renderX, renderY);
		bitmap.draw(Art.i.button[1][2], renderX + width, renderY);
        
        Text.renderCenter(text, bitmap, renderX + width / 2, renderY + ((height / 2) - 4));
	}
	
	public void tick() {
		this.renderX = xa;
		this.renderY = ya;
	}
}
