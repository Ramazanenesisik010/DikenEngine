package me.ramazanenescik04.diken.game.entity;

import me.ramazanenescik04.diken.game.GameObject;

public abstract class Entity extends GameObject {
	private static final long serialVersionUID = 1L;
	public boolean removed = false;
	public int health = 100, maxHealth = 100; // Default health value

	public Entity(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.name = "Entity";
	}
	
	public Entity(int x, int y) {
		super(x, y);
		this.name = "Entity";
	}
	
	//API START
	
	public void remove() {
		this.removed = true;
	}
	
	public void kill() {
		this.health = -1; // Set health to -1 to indicate death
	}
	
	public boolean isDead() {
		return this.health <= 0;
	}
	
    //API END
}
