package com.emirenesgames.engine.gui;

import java.util.Properties;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.gui.background.DownBackground;

public class DES extends Screen {

	private Screen parent;
	private Properties newProperties = new Properties();

	public DES(Screen parent) {
		this.parent = parent;
	}
	
	public void tick() {
		super.tick();
		
		if (((CheckBox)this.buttons.get(0)).isChecked()) {
			newProperties.setProperty("console", "true");
		} else {
			newProperties.setProperty("console", "false");
		}
		
		if (((CheckBox)this.buttons.get(1)).isChecked()) {
			newProperties.setProperty("show_fps", "true");
		} else {
			newProperties.setProperty("show_fps", "false");
		}

		if (((CheckBox)this.buttons.get(2)).isChecked()) {
			newProperties.setProperty("fullscreen", "true");
		} else {
			newProperties.setProperty("fullscreen", "false");
		}
	}

	public void render(Bitmap screen) {
		super.render(screen);
		
		Text.render("Ayarlar", screen, 10, 3, UniFont.getFont("Dialog.bold.24"));
		
		Text.render("Konsol", screen, 10, 3 + 24 * 3, UniFont.getFont("Dialog.plain.12"));
		Text.render("Görüntü", screen, 10, (3 + 24 * 5), UniFont.getFont("Dialog.plain.12"));
	}

	protected void actionListener(int id) {
		if (id == 1) {
			this.engine.setCurrentScreen(parent);
		}

		if (id == 2) {
			this.engine.gManager.config = newProperties;
			this.engine.setCurrentScreen(parent);
		}
	}

	public void openScreen() {
		this.newProperties = (Properties) this.engine.gManager.config.clone();
		this.setBackground(new DownBackground(Art.i.bgd_tiles[0][0]));
		this.buttons.clear();
		this.buttons.add(new CheckBox("Konsol'u Etkinleştir", 10, (3 + (3 * 24)) + 12));
		this.buttons.add(new CheckBox("FPS'i Göster", 10,  (3 + 24 * 5) + 14));
		this.buttons.add(new CheckBox("Tam Ekran", 10,  (3 + 22 * 6) + (14 * 2)));
		this.buttons.add(new Button("Vazgeç", 10, DikenEngine.HEIGHT - (1 * 20), 100, 15, 1));
		this.buttons.add(new Button("Tamam", 10 + 106, DikenEngine.HEIGHT - (1 * 20), 100, 15, 2));
		
		((CheckBox)this.buttons.get(0)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("console")));
		((CheckBox)this.buttons.get(1)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("show_fps")));
		((CheckBox)this.buttons.get(2)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("fullscreen")));
	}

}