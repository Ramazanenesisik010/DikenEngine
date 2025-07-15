package me.ramazanenescik04.diken.game;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.Hitbox;
import me.ramazanenescik04.diken.resource.Bitmap;

public class GameObject extends Hitbox {
	private static final long serialVersionUID = 1L;
	
	protected String name; // Object name, used for identification
	private boolean isVisible = true; // Object visibility status

	public GameObject(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public GameObject(int x, int y) {
		super(x, y);
	}
	
	public Bitmap render() {
		return new Bitmap(16, 16);
	}
	
	public void tick(World world, DikenEngine engine) {
	}
	
	public void objectCollided(World world, DikenEngine engine, GameObject other) {
		// Default implementation does nothing
	}
	
	// API START
	
	public GameObject setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public GameObject setVisible(boolean visible) {
		this.isVisible = visible;
		return this;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	// API END
}
