package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Bitmap;

public abstract class GuiObject extends Hitbox implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public GuiObject(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Bitmap render() {
		return new Bitmap(width, height);
	}
	
	public void tick() {
	}
	
}
