package com.emirenesgames.engine.gui;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;

public class TextField {
	
	private Hitbox hitbox;
	private boolean isFocused = false;
	
	private String text = "";
	private DikenEngine engine;
	
	private int counter;
	
	public TextField(int x, int y, int width, int height, DikenEngine engine) {
		this.hitbox = new Hitbox(x, y, width, height);
		this.engine = engine;
	}
	
	public void tick() {
		
		if(this.hitbox.intersects(new Rectangle(this.engine.mouse.x, this.engine.mouse.y, 2, 2))){
			if (!isFocused && this.engine.input.mb0) {
			    this.engine.input.mb0 = false;
			    this.isFocused = true;
		    } else if (isFocused && this.engine.input.mb0){
		    	this.engine.input.mb0 = false;
		    	this.isFocused = false;
		    }
		
		} else if (this.engine.input.mb0){
			this.engine.input.mb0 = false;
			this.isFocused = false;
		}
		
		if(isFocused) {
			if(this.engine.input.keysDown[KeyEvent.VK_CONTROL] && this.engine.input.keysDown[KeyEvent.VK_V]) {
				this.engine.input.keysDown[KeyEvent.VK_V] = false;
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				
				try {
					this.setText(this.getText() + (String) clipboard.getData(DataFlavor.stringFlavor));
				} catch (UnsupportedFlavorException e) {
				} catch (IOException e) {
				}
			}
			if(this.engine.input.keysDown[KeyEvent.VK_CONTROL] && this.engine.input.keysDown[KeyEvent.VK_C]) {
				this.engine.input.keysDown[KeyEvent.VK_C] = false;
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(text), null);

			}
			if(this.engine.input.keysDown[KeyEvent.VK_CONTROL] && this.engine.input.keysDown[KeyEvent.VK_X]) {
				this.engine.input.keysDown[KeyEvent.VK_X] = false;
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(text), null);
				
				this.setText("");
			}
		}
		++this.counter;
	}
	
	public void keyPressed(char var1) {
		UniFont defaultFont = DikenEngine.getEngine().defaultFont;
		if(isFocused) {
			if (this.engine.input.keysDown[KeyEvent.VK_BACK_SPACE] && this.text.length() > 0) {
				this.engine.input.keysDown[KeyEvent.VK_BACK_SPACE] = false;
				text = text.substring(0, text.length() - 1);
			} else {
				if((defaultFont.charTypes.indexOf(var1) >= 0) && !(Text.stringBitmapWidth(text + var1, defaultFont) > hitbox.width - Text.stringBitmapWidth("" + var1, defaultFont))) {
					  this.text = this.text + var1;
				}
			}
		}
	}
	
	public void render(Bitmap bitmap) {
		bitmap.box(this.hitbox.x, this.hitbox.y, this.hitbox.x + this.hitbox.width, this.hitbox.y + this.hitbox.height, isFocused() ? 0xffffff00 : 0xffffffff);
		bitmap.fill(this.hitbox.x + 1, this.hitbox.y + 1, (this.hitbox.x + this.hitbox.width) - 1, (this.hitbox.y + this.hitbox.height) - 1, 0xff282828);
		String text = this.text;
		
		if(isFocused) {
			text = text + (this.counter / 6 % 12 > 6?"_":"");
		}
		Text.render(text, bitmap, this.hitbox.x + 2, this.hitbox.y + 2);
	}

	public boolean isFocused() {
		return isFocused;
	}

	public void setFocused(boolean isFocused) {
		this.isFocused = isFocused;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	

}
