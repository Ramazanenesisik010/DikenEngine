package me.ramazanenescik04.diken.gui.screen;

import java.net.URI;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.compoment.Button;
import me.ramazanenescik04.diken.gui.compoment.LinkButton;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.resource.ArrayBitmap;
import me.ramazanenescik04.diken.resource.Bitmap;
import me.ramazanenescik04.diken.resource.Language;
import me.ramazanenescik04.diken.resource.ResourceLocator;

public class DefaultMainMenuScreen extends Screen {
	
	public void render(Bitmap screen) {
		super.render(screen);
	}   

	/*public void actionListener(int id) {
		if(id == 0) {
			engine.setCurrentScreen(new PerformaceTestScreen(this));
		}
		if(id == 1) {
			engine.setCurrentScreen(new ConsoleScreen(this));
		}
		if(id == 2) {
			engine.gManager.saveConfig();
			Display.destroy();	
			System.exit(0);
		}
        if(id == 4) {
			engine.setCurrentScreen(new SoundTest(this));
		}
        // Silinecek
		if(id == 3) {
			try {
				if(Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					desktop.browse(new URI("https://github.com/Ramazanenesisik010/DikenEngine"));
				}
			} catch (IOException e) {
			} catch (URISyntaxException e) {
			}
		}
		if(id == 5) {
			engine.setCurrentScreen(new DES(this));
		}
	}*/

	public void openScreen() {		
		ArrayBitmap icon = (ArrayBitmap) ResourceLocator.getResource("bgd-tiles");
		DMMPanel panel = new DMMPanel(engine.getWidth(), engine.getHeight());
		panel.setBackground(new DownBackground(icon.bitmap[0][0]));		
		this.setContentPane(panel);
	}
	
	public void closeScreen() {
	}
	
	private static class DMMPanel extends Panel {
		private static final long serialVersionUID = 1L;

		public DMMPanel(int width, int height) {
			super(0, 0, width, height);
		}
		
		public Bitmap render() {
			Bitmap bitmap = super.render();
			bitmap.draw((Bitmap) ResourceLocator.getResource("icon-x16"), 10, (100 - 32) - 10);
			Text.render("DikenEngine", bitmap, (10 + 32) + 2, (100 - 32) - 10);
			Text.render(DikenEngine.VERSION, bitmap, (10 + 32) + 2, ((100 - 32) - 10) + 9);
			return bitmap;
		}

		public void init(DikenEngine engine) {
			Language lang = Language.i;
			
			clearCompoments();
			Button performaceButton = new Button(lang.languageValue("dmainmenu.editor"), 10, 100, 200, 15).setRunnable(() -> {
				//engine.setCurrentScreen(new DikenEditorScreen(engine.getCurrentScreen()));
			});
			performaceButton.active = false;
			addCompoment(performaceButton);
			addCompoment(new Button(lang.languageValue("dmainmenu.setting"), 10, 100 + (1 * 20), 200, 15));
			addCompoment(new Button(lang.languageValue("dmainmenu.exit"), 10, 100 + (2 * 20), 200, 15).setRunnable(() -> {
				engine.close();
			}));
			addCompoment(new LinkButton("Github", 10, engine.getHeight() - (1 * 20), 200, 15).setURI(URI.create("https://github.com/Ramazanenesisik010/DikenEngine")));
		}
		
	}
}
