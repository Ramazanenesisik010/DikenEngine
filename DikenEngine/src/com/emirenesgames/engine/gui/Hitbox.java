package com.emirenesgames.engine.gui;

import java.awt.Rectangle;

public class Hitbox extends Rectangle {
	private static final long serialVersionUID = 1L;
	public boolean active = true;
	
	public Hitbox(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public Hitbox(int x, int y) {
		super(x, y, 1, 1);
	}

	public boolean intersects(Rectangle r) {
	    return this.active ? super.intersects(r) : false;
	}

}
