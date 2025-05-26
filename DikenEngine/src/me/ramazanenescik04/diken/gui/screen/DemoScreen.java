package me.ramazanenescik04.diken.gui.screen;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.resource.Bitmap;

public class DemoScreen extends Screen {
	private Screen parentScreen;
	
	public DemoScreen(Screen currentScreen) {
		this.parentScreen = currentScreen;
	}
	
	

	private static class DemoScreenPanel extends Panel {
		private static final long serialVersionUID = 1L;

		public DemoScreenPanel() {
			super(0, 0, 100, 100);
			this.setBackground(new StaticBackground(Bitmap.createClearedBitmap(100, 100, 0xFF0000FF)));
		}
		
		@Override
		public void init(DikenEngine engine) {
			super.init(engine);
		}
		
		@Override
		public Bitmap render() {
			return super.render();
		}
	}

}
