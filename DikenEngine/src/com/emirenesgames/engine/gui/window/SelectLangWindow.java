package com.emirenesgames.engine.gui.window;

import java.io.IOException;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.Language;
import com.emirenesgames.engine.gui.Button;
import com.emirenesgames.engine.gui.Text;
import com.emirenesgames.engine.gui.UniFont;

public class SelectLangWindow extends Window {
	private static final long serialVersionUID = 1L;
	private Language lang = Language.i;

	public SelectLangWindow() {
		super("SelectLangWindow", DikenEngine.WIDTH / 2 - 150 / 2, DikenEngine.HEIGHT / 2 - 150 / 2, 150, 150, Art.i.default_win_icons[1][0]);
		this.resizable = false;
		this.title = lang.languageValue("lang.title");
		// TODO Auto-generated constructor stub
	}

	protected void actionListener(int id) {
		if(id == 1) {
			try {
				this.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void actionListener(Button button) {
		if(button.id >= 100) {
			engine.gManager.config.setProperty("lang", lang.languageTable.keySet().toArray(new String[] {})[button.id - 100]);
			this.open();
		}
	}

	public void renderPanel(Bitmap screen) {
		screen.fill(0, 0, width, height, 0xff00ffff);
		super.renderPanel(screen);
		Text.render(lang.languageValue("lang.title"), screen, 10, 3, UniFont.getFont("Dialog.bold.24"));
	}
	
	private void loadLangOption() {
		String[] keys = lang.languageTable.keySet().toArray(new String[] {});
		for (int i = 0; i < keys.length; i++) {
			this.buttons.add(new Button(lang.languageValue("lang.name", keys[i]), 10, 40 + (21 * i), 100, 15, 100 + i));
		}
	}

	public void open() {
		this.buttons.clear();
		loadLangOption();
		this.buttons.add(new Button(lang.languageValue("gui.done"), 10, height - BASLIK_YUKSEKLIGI - (1 * 24), 100, 15, 1));
	}

}
