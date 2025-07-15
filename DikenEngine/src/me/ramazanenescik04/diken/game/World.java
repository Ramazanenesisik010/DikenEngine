package me.ramazanenescik04.diken.game;

import java.awt.Point;
import java.util.Collection;
import java.util.List;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.game.entity.Entity;
import me.ramazanenescik04.diken.gui.compoment.GuiCompoment;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.resource.Bitmap;

public class World extends Panel {
	private static final long serialVersionUID = 1L;
	private boolean isPlatformMap = false; //RPG OR PLATFORM (SONIC, MARIO, ETC.) MAP --TR: rpg yada platform haritası
	private List<GameObject> objects;
	private List<Entity> entities;
	
	public Point camera;
	
	public boolean isPlatformMap() {
		return isPlatformMap;
	}
	
	private World() {
	}
	
	public static World createWorld(boolean _platform, Collection<GameObject> gameObjects, Collection<Entity> gameEntities) {
		World world = new World();
		world.isPlatformMap = _platform;
		world.objects = new java.util.ArrayList<>(gameObjects);
		world.entities = new java.util.ArrayList<Entity>(gameEntities);
		
		return world;
	}
	
	public void addObject(GameObject obj) {
		this.objects.add(obj);
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void setCamera(Point camera) {
		this.camera = camera;
	}
	
	public Point getCamera() {
		return camera;
	}
	
	public void removeObject(GameObject obj) {
		this.objects.remove(obj);
	}
	
	public void removeEntity(Entity entity) {
		this.entities.remove(entity);
	}
	
	public GameObject getObjectAt(int x, int y) {
		for (GameObject obj : objects) {
			if (obj.getX() >= x && obj.getY() >= y && obj.width <= x && obj.height <= y) {
				return obj;
			}
		}
		return null;
	}
	
	public Entity getEntityAt(int x, int y) {
		for (Entity entity : entities) {
			if (entity.getX() >= x && entity.getY() >= y && entity.width <= x && entity.height <= y) {
				return entity;
			}
		}
		return null;
	}
	
	public int getObjectCount() {
		return objects.size();
	}
	
	public int getEntityCount() {
		return entities.size();
	}
	
	public GameObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			return null;
		}
		return objects.get(index);
	}
	
	public Entity getEntity(int index) {
		if (index < 0 || index >= entities.size()) {
			return null;
		}
		return entities.get(index);
	}

	public Bitmap render() {
		Bitmap worldBitmap = new Bitmap(width, height);
		
		if (this.drawX) {
			worldBitmap.box(0, 0, width - 1, height - 1, 0xffffffff);
			worldBitmap.drawLine(0, 0, this.width, this.height, 0xffffffff, 1);
			worldBitmap.drawLine(this.width, 0, 0, this.height, 0xffffffff ,1);
		}
		
		if (this.background != null) {
			this.background.render(worldBitmap);
		}
		
		for (GameObject obj : objects) {
			Bitmap objBitmap = obj.render();
			if (objBitmap != null) {
				worldBitmap.draw(objBitmap, obj.x - camera.x, obj.y - camera.y);
			}
		}
		
		for (Entity entity : entities) {
			Bitmap entityBitmap = entity.render();
			if (entityBitmap != null) {
				worldBitmap.draw(entityBitmap, entity.x - camera.x, entity.y - camera.y);
			}
		}
		
		List<GuiCompoment> compoments = this.getCompoments();
		for (GuiCompoment compoment : compoments) {
			worldBitmap.draw(compoment.render(), compoment.x, compoment.y);
		}
		
		return worldBitmap;
	}

	public void tick(DikenEngine engine) {
		for (GameObject obj : objects) {
			obj.tick(this, engine);
		}
		
		for (Entity entity : entities) {
			if (entity.isDead()) {
				entity.remove();
				continue; // Skip further processing for dead entities
			}
			
			if (entity.removed) {
				entities.remove(entity);
				continue; // Skip further processing for removed entities
			}
			
			entity.tick(this, engine);
		}
		
		super.tick(engine);
	}

	public void init(DikenEngine engine) {
		
	}
}
