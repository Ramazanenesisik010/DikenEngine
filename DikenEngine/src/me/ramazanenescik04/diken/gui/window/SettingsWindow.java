package me.ramazanenescik04.diken.gui.window;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.SystemInfo;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.gui.screen.StaticBackground;
import me.ramazanenescik04.diken.resource.Bitmap;

public class SettingsWindow extends Window {
	private static final long serialVersionUID = 1L;

	public SettingsWindow() {
		super(2, 2, 200, 200);
		this.setTitle("Settings");
	}
	
	public void open() {
		this.setContentPane(new SettingsPanel());
	}
	
	public static class SettingsPanel extends Panel {
		private static final long serialVersionUID = 1L;

		public SettingsPanel() {
		}
		
		public void init(DikenEngine engine) {
			setBackground(new StaticBackground(Bitmap.createClearedBitmap(16, 16, 0xffa0a0a0)));
		}
		
		private SystemInfo.OS os = SystemInfo.instance.getOS();
		
		public Bitmap render() {
			Bitmap bitmap = super.render();
			Text.render("Settings", bitmap, 2, 2);
			Text.render("OS: " + os.name(), bitmap, 2, 12);
			return bitmap;
		}		
	}

}
