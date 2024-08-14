package com.emirenesgames.engine.gui;

import java.awt.Rectangle;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;

public class Button extends Hitbox {
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
        for(int i = 0; i < width; i++) {
			bitmap.draw(Art.i.button[0][1], renderX + i, renderY);
		}
        bitmap.draw(Art.i.button[0][0], renderX, renderY);
        bitmap.draw(Art.i.button[0][2], renderX + width - 4, renderY);
        
        Text.renderCenter(text, bitmap, renderX + width / 2, renderY + ((height / 2) - 4));
	}
	
	public void tick() {
		this.renderX = xa;
		this.renderY = ya;
	}
}
