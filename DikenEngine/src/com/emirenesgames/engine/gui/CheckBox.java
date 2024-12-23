package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;

public class CheckBox extends GuiObject {
	private static final long serialVersionUID = 1L;
	
	private boolean checked;
	public String text;

	public CheckBox(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.text = text;
	}
	
	public CheckBox(String text, int x, int y) {
		super(x, y, 20, 20);
		this.text = text;
	}

	public Bitmap render() {
		Bitmap bitmap = new Bitmap((width + 2) + Text.stringBitmapWidth(text, DikenEngine.getEngine().defaultFont), height + 4);
		bitmap.draw(new Button("", 0, 0, width, height, 0).render(), 0, 0);
		if(checked) {
			bitmap.draw(Art.i.check_box[1][0], 0, 0);
		}
		return bitmap;
	}
	
	public void setCheck(boolean check) {
		this.checked = check;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public boolean intersects(Hitbox r) {
		boolean b = super.intersects(r);
		return b;
	}
	
	public void click() {
		checked = !checked;
	}

}
