package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.Bitmap;

public class ImageButton extends Button {
	private static final long serialVersionUID = 1L;
	
	private Bitmap img;

	public ImageButton(Bitmap img, int x, int y, int width, int height, int id) {
		super("", x, y, width, height, id);
		this.img = img;
	}

	public Bitmap render() {
		Bitmap bitmap = super.render();
		if (img != null) {
			bitmap.draw(img, ((width + 4) / 2) - (img.w / 2), ((height + 4) / 2) - (img.h / 2));		
		}
		return bitmap;
	}
	
	

}
