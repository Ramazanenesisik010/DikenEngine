package me.ramazanenescik04.diken.gui;

import java.awt.Rectangle;

public class Hitbox extends Rectangle {
	private static final long serialVersionUID = 1L;
	public boolean active = true;
	
	public Hitbox(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		if (width < 1) width = 1;
		if (height < 1) height = 1;
		
		this.width = width;
		this.height = height;
	}
	
	public Hitbox(int x, int y) {
		super(x, y, 1, 1);
	}

	public boolean intersects(Rectangle r) {
	    return this.active ? super.intersects(r) : false;
	}
	
	public boolean contains(int x, int y) {
		return this.active && super.contains(x, y);
	}
	
	public boolean contains(Rectangle r) {
		return this.active && super.contains(r);
	}
	
	public Hitbox setActive(boolean active) {
		this.active = active;
		return this;
	}
	
	public boolean isActive() {
		return active;
	}

}
