package me.ramazanenescik04.diken.gui.screen;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.InputHandler;
import me.ramazanenescik04.diken.gui.compoment.GuiCompoment;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.resource.Bitmap;

public abstract class Screen {
	private List<GuiCompoment> guiObjects = new ArrayList<>();
	public DikenEngine engine;
	
	private IBackground background;
	public boolean renderBackground = true;
	
	public void tick() {
		if (renderBackground && background != null) {
			background.tick();
		}
		
		for (GuiCompoment guiObject : guiObjects) {
			guiObject.tick(engine);
		}
	}
	
	public void keyboardEveent() {
		this.keyDown( Keyboard.getEventCharacter(), Keyboard.getEventKey());
	}

	public void keyDown(char eventCharacter, int eventKey) {
		for (GuiCompoment guiObject : guiObjects) {
			guiObject.keyPressed(eventCharacter, eventKey);
		}
	}
	
	public void mouseClickEvent() {
		int mouseX = (int) (Mouse.getEventX() / engine.scale);
		int mouseY = (int) (engine.getHeight() - (Mouse.getEventY() / engine.scale));
		this.mouseClick(mouseX, mouseY, Mouse.getEventButton());
	}

	private void mouseClick(int mouseX, int mouseY, int eventButton) {
		for (int i = 0; i < guiObjects.size(); i++) {
			GuiCompoment guiObject = guiObjects.get(i);
			boolean isTouch = false;
			if (guiObject.intersects(InputHandler.getMouseHitbox())) {
				isTouch = true;
			}
			guiObject.mouseClicked(mouseX, mouseY, eventButton, isTouch);
		}
	}
	
	public void addGuiObject(GuiCompoment guiObject) {
		if (guiObject == null) {
			return;
		}
		
		if (guiObject instanceof Panel) {
			Panel panel = (Panel) guiObject;
			panel.init(this.engine);
		}
		guiObjects.add(guiObject);
	}
	
	public void removeGuiObject(GuiCompoment guiObject) {
		if (guiObject == null) {
			return;
		}
		
		guiObjects.remove(guiObject);
	}
	
	public void removeGuiObject(int index) {
		if (index < 0 || index >= guiObjects.size()) {
			return;
		}
		
		guiObjects.remove(index);
	}
	
	public GuiCompoment getGuiObject(int index) {
		if (index < 0 || index >= guiObjects.size()) {
			return null;
		}
		
		return guiObjects.get(index);
	}
	
	public void clearGuiObjects() {
		guiObjects.clear();
	}
	
	public void openScreen() {
	}
	   
	public void closeScreen() {
	}

	public void render(Bitmap bitmap) {
		if (renderBackground && background != null) {
			background.render(bitmap);
		}
		
		for (GuiCompoment guiObject : guiObjects) {
			bitmap.draw(guiObject.render(), guiObject.x, guiObject.y);
		}
	}
	
	public void setBackground(IBackground background) {
		this.background = background;
	}

	private void mouseMovingEvent() {
		int mouseX = (int) (Mouse.getEventX() / engine.scale);
		int mouseY = (int) (engine.getHeight() - (Mouse.getEventY() / engine.scale));
		
		for (int i = 0; i < guiObjects.size(); i++) {
			GuiCompoment guiObject = guiObjects.get(i);
			boolean isTouch = false;
			if (guiObject.intersects(InputHandler.getMouseHitbox())) {
				isTouch = true;
			}
			guiObject.mouseGetInfo(mouseX, mouseY, isTouch);
		}
	}

	public void mouseEvent() {
		mouseMovingEvent();
		
		if (Mouse.getEventButtonState()) {
			mouseClickEvent();
		}
	}

}
