package me.ramazanenescik04.diken.gui.compoment;

import java.net.URI;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.resource.ArrayBitmap;
import me.ramazanenescik04.diken.resource.Bitmap;
import me.ramazanenescik04.diken.resource.ResourceLocator;

public class LinkButton extends GuiLink {
	private static final long serialVersionUID = 1L;
	private Button button;

	public LinkButton(String text, int x, int y, int width, int height, int color) {
		super(x, y, width, height);
		button = new Button(text, x, y, width, height, color);
	}
	
	public LinkButton(String text, int x, int y, int width, int height) {
		super( x, y, width, height);
		button = new Button(text, x, y, width, height);
	}
	
	public LinkButton setURI(URI uri) {
		_setURI(uri);
		return this;
	}
	
	public Bitmap render() {
		Bitmap bitmap = button.render();
		ArrayBitmap button = (ArrayBitmap) ResourceLocator.getResource("button-array");
		bitmap.draw(button.bitmap[3][0], width - 9, height / 2 - 2);
		return bitmap;
	}

	public void tick(DikenEngine engine) {
		button.tick(engine);
	}
	
	
}
