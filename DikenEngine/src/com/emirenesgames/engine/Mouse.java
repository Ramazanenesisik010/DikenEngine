package com.emirenesgames.engine;

import java.awt.Point;

public class Mouse {
	
	public int x;
	public int y;
	
	public boolean mouseDragged;
	public boolean mouseMoved;
	public boolean mouseEntered;
	public boolean mouseExited;
	
	public void update(int x, int y, boolean mouseDragged2, boolean mouseMoved2, boolean mouseEntered2, boolean mouseExited2) {
		this.x = x;
		this.y = y;
		
		this.mouseDragged = mouseDragged2;
		this.mouseMoved = mouseMoved2;
		this.mouseEntered = mouseEntered2;
		this.mouseExited = mouseExited2;
	}
	
	public Point getPoint() {
        int x;
        int y;
        synchronized (this) {
            x = this.x;
            y = this.y;
        }
        return new Point(x, y);
    }

}
