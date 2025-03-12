package com.emirenesgames.engine.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;

public class TextField extends GuiObject{
	private static final long serialVersionUID = 1L;
	private boolean isFocused = false;
	public boolean isNumberField = false;
	
	private String text = "";
	private DikenEngine engine;
	
	private int counter;
	
	public TextField(int x, int y, int width, int height, DikenEngine engine) {
		super(x, y, width, height);
		this.engine = engine;
	}
	
	public TextField(String text, int x, int y, int width, int height, DikenEngine engine) {
		this(x, y, width, height, engine);
		this.text = text;
	}
	
	public void tick() {	
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
		
		if(this.engine.wManager.activeWindow != null && this.engine.wManager.findActiveWindow2(this.engine.mouse.getPoint())) {
			if (!this.engine.wManager.activeWindow.guiObjetIsPressed(this.engine.mouse.getPoint(), this) && this.engine.input.mb0) {
				this.isFocused = false;
			}
		} else if(!intersects(new Hitbox(this.engine.mouse.x - 1, this.engine.mouse.y - 1)) && this.engine.input.mb0) {
			this.isFocused = false;
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
				if (isNumberField) {
					if(("-0123456789".indexOf(var1) >= 0) && !(Text.stringBitmapWidth(text + var1, defaultFont) > width - Text.stringBitmapWidth("" + var1, defaultFont))) {
						  this.text = this.text + var1;
					}
				} else {
					if((defaultFont.charTypes.indexOf(var1) >= 0) && !(Text.stringBitmapWidth(text + var1, defaultFont) > width - Text.stringBitmapWidth("" + var1, defaultFont))) {
						  this.text = this.text + var1;
					}
				}
				
			}
		}
	}
	
	public Bitmap render() {
		Bitmap bitmap = new Bitmap(this.width + 1, this.height + 1);
		bitmap.box(0, 0, this.width, this.height, isFocused() ? 0xffffff00 : 0xffffffff);
		bitmap.fill(1, 1, this.width - 1, this.height - 1, 0xff282828);
		String text = this.text;
		
		if(isFocused) {
			text = text + (this.counter / 6 % 12 > 6?"_":"");
		}
		Text.render(text, bitmap, 2, 2);
		return bitmap;
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
	
	public TextField setNumberic() {
		this.isNumberField = true;
		return this;
	}

}
