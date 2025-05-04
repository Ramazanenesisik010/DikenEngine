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
	//private List<GuiCompoment> guiObjects = new ArrayList<>();
	private Panel contentPane = new Panel(0, 0, 1, 1);
	public DikenEngine engine;
	
	private IBackground background;
	public boolean renderBackground = true;
	
	public void tick() {
		if (renderBackground && background != null) {
			background.tick();
		}
		
		contentPane.setSize(engine.getWidth(), engine.getHeight());
		contentPane.tick(engine);
	}
	
	public void keyboardEveent() {
		this.keyDown( Keyboard.getEventCharacter(), Keyboard.getEventKey());
	}

	public void keyDown(char eventCharacter, int eventKey) {
		contentPane.keyPressed(eventCharacter, eventKey);
	}
	
	public void mouseClickEvent() {
		int mouseX = (int) (Mouse.getEventX() / engine.scale);
		int mouseY = (int) (engine.getHeight() - (Mouse.getEventY() / engine.scale));
		this.mouseClick(mouseX, mouseY, Mouse.getEventButton());
	}

	private void mouseClick(int mouseX, int mouseY, int eventButton) {
		contentPane.mouseClicked(mouseX, mouseY, eventButton, InputHandler.isMouseOnScreen());
	}
	
	public void openScreen() {
	}
	   
	public void closeScreen() {
	}

	public void render(Bitmap bitmap) {
		if (renderBackground && background != null) {
			background.render(bitmap);
		}
		
		bitmap.draw(contentPane.render(), contentPane.x, contentPane.y);
	}
	
	public void setBackground(IBackground background) {
		this.background = background;
	}

	private void mouseMovingEvent() {
		int mouseX = (int) (Mouse.getEventX() / engine.scale);
		int mouseY = (int) (engine.getHeight() - (Mouse.getEventY() / engine.scale));
		
		contentPane.mouseGetInfo(mouseX, mouseY, true);
	}

	public void mouseEvent() {
		mouseMovingEvent();
		
		if (Mouse.getEventButtonState()) {
			mouseClickEvent();
		}
	}
	
	public Panel getContentPane() {
		return contentPane;
	}
	
	public void setContentPane(Panel panel) {
		if (panel == null)
			return;
		
		panel.init(engine);
		this.contentPane = panel;
	}

}
