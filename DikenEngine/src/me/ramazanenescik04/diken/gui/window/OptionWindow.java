package me.ramazanenescik04.diken.gui.window;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.UniFont;
import me.ramazanenescik04.diken.gui.compoment.Button;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.gui.compoment.RenderImage;
import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.gui.screen.StaticBackground;
import me.ramazanenescik04.diken.resource.*;

public class OptionWindow extends Window {
	private static final long serialVersionUID = 1L;
	
	public static final int INFO_MESSAGE = 0;
	public static final int ERROR_MESSAGE = 1;
	public static final int WARNING_MESSAGE = 2;
	public static final int PLAIN_MESSAGE = 3;
	
	private String message;

	public OptionWindow(String message, String title, Bitmap icon) {
		super(2, 2, 100, 50);
		if (icon == null)
			icon = IOResource.missingTexture;

		this.setTitle(title);
		this.setIcon(icon);
		this.setContentPane(new OptionPanel(icon, (this.message = message)));
	}

	protected void open() {
		resized();
	}
	
	private static class OptionPanel extends Panel {

		private static final long serialVersionUID = 1L;

		private String message;
		private Bitmap icon;
		public OptionPanel(Bitmap icon, String message) {
			this.message = message;
			this.icon = icon;
		}

		public void init(DikenEngine engine) {
			setBackground(new StaticBackground(Bitmap.createClearedBitmap(16, 16, 0xffa0a0a0)));
		}
		
		public Bitmap render() {
			Bitmap bitmap = super.render();
			bitmap.draw(icon, 2, 2);
			Text.render(message, bitmap, 25, 2);
			return bitmap;
		}
	}
	
	private void setSizeAuto() {
		UniFont defaultFont = DikenEngine.getEngine().defaultFont;
		int w = Text.stringBitmapAverageWidth(message, defaultFont);
		int h = Text.stringBitmapAverageHeight(message, defaultFont);
		int width = w;
		int height = (int)message.lines().count() * 2 * h;
		
		if (width < 100)
			width = 100;
		
		if (height < 50)
			height = 50;
		
		this.setSize(width + 20, height + 20);
	}
	
	public void resized() {
		setSizeAuto();
		super.resized();
	}

	public static void showMessage(String message, String title, int messageType) {
		if (title == null)
			title = "Option Window";
		
		if (message == null)
			message = "No message";
		
		ArrayBitmap iconList = (ArrayBitmap) ResourceLocator.getResource("win-icons");
		Bitmap icon = IOResource.missingTexture;
		
		switch (messageType) {
			case INFO_MESSAGE:
				icon = iconList.getBitmap(5, 0);
				break;
				
			case ERROR_MESSAGE:
				icon = iconList.getBitmap(2, 0);
				break;
				
			case WARNING_MESSAGE:
				icon = iconList.getBitmap(4, 0);
				break;
				
			case PLAIN_MESSAGE:
				icon = iconList.getBitmap(3, 0);
				break;
		}
		
		DikenEngine.getEngine().wManager.addWindow(new OptionWindow(message, title, icon));
		
		
	}
}
