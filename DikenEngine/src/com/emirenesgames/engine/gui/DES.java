package com.emirenesgames.engine.gui;

import java.util.Properties;

import javax.swing.JOptionPane;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.GameManager;
import com.emirenesgames.engine.Language;
import com.emirenesgames.engine.gui.background.DownBackground;
import com.emirenesgames.engine.gui.window.SelectLangWindow;

public class DES extends Screen {

	private Screen parent;
	public Properties newProperties = new Properties();
	public int addsa;
	Language lang = Language.i;

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
		
		if (((CheckBox)this.buttons.get(3)).isChecked()) {
			newProperties.setProperty("sync", "true");
		} else {
			newProperties.setProperty("sync", "false");
		}
		
		if (((CheckBox)this.buttons.get(5)).isChecked()) {
			newProperties.setProperty("legacy-crash", "true");
		} else {
			newProperties.setProperty("legacy-crash", "false");
		}
		
		if (((CheckBox)this.buttons.get(6)).isChecked()) {
			newProperties.setProperty("debug", "true");
		} else {
			newProperties.setProperty("debug", "false");
		}
		
		if (((CheckBox)this.buttons.get(7)).isChecked()) {
			newProperties.setProperty("antialiasing", "true");
		} else {
			newProperties.setProperty("antialiasing", "false");
		}
	}

	public void render(Bitmap screen) {
		super.render(screen);
		
		Text.render(lang.languageValue("options.title"), screen, 10, 3, UniFont.getFont("Dialog.bold.24"));
		
		Text.render(lang.languageValue("options.console"), screen, 10, 3 + 24 * 3, UniFont.getFont("Dialog.plain.12"));
		Text.render(lang.languageValue("options.display"), screen, 10, (3 + 24 * 5), UniFont.getFont("Dialog.plain.12"));
		Text.render(lang.languageValue("options.maxfps") + ": ", screen, 10, ((3 + 24 * 8) + (14 * 4)) - 3);
		Text.render(lang.languageValue("options.expermental"), screen, 270, 3 + 24 * 3, UniFont.getFont("Dialog.plain.12"));
	}

	protected void actionListener(int id) {
		if (id == 0) {
			if(!this.engine.gManager.config.equals(newProperties)) {
				int a = JOptionPane.showConfirmDialog(engine, "Ayarları Kaydetmek İster Misiniz", "Ayarları Kaydet?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(a == 2) {
					return;
				}
				
				if(a == 0) {
					this.saveButtonClicked();
					this.engine.TARGET_FPS = Integer.parseInt(((TextField)this.buttons.get(4)).getText());
					this.engine.gManager.config = newProperties;
					this.engine.gManager.saveConfig();
				}
					
				this.engine.wManager.addWindow(new SelectLangWindow());
			} else {
				this.engine.wManager.addWindow(new SelectLangWindow());
			}
			
		}
		
		if (id == 1) {
			this.engine.setCurrentScreen(parent);
		}

		if (id == 2) {
			this.saveButtonClicked();
			this.engine.TARGET_FPS = Integer.parseInt(((TextField)this.buttons.get(4)).getText());
			this.engine.gManager.config = newProperties;
			this.engine.gManager.saveConfig();
			this.engine.setCurrentScreen(parent);
		}
		
		if (id == 3) {
			this.newProperties = GameManager.defaultConfig;
			this.engine.gManager.config = newProperties;
			this.engine.setCurrentScreen(this);
		}
		
		if (id == 4) {
			this.saveButtonClicked();
			this.engine.TARGET_FPS = Integer.parseInt(((TextField)this.buttons.get(4)).getText());
			this.engine.gManager.config = newProperties;
			this.engine.gManager.saveConfig();
			this.engine.setCurrentScreen(this);
		}
	}
	
	protected void saveButtonClicked() {
	}

	public void openScreen() {
		this.newProperties = (Properties) this.engine.gManager.config.clone();
		this.setBackground(new DownBackground(Art.i.bgd_tiles[0][0]));
		this.buttons.clear();
		this.buttons.add(new CheckBox(lang.languageValue("options.enableconsole"), 10, (3 + (3 * 24)) + 12));
		this.buttons.add(new CheckBox(lang.languageValue("options.showfps"), 10,  (3 + 24 * 5) + 14));
		this.buttons.add(new CheckBox(lang.languageValue("options.fullscreen"), 10,  (3 + 22 * 6) + (14 * 2)));
		this.buttons.add(new CheckBox(lang.languageValue("options.sync"), 10,  (3 + 24 * 6) + (14 * 3)));
		this.buttons.add(new TextField(this.engine.TARGET_FPS + "", 10,  (3 + 22 * 9) + (14 * 4), 100, 16, this.engine).setNumberic());
		this.buttons.add(new CheckBox(lang.languageValue("options.legacycrash"), 270, 3 + (24 * 3 + 16)));
		this.buttons.add(new CheckBox(lang.languageValue("options.debug"), 270, 3 + (24 * 3 + (16 + 26))));
		this.buttons.add(new CheckBox(lang.languageValue("options.antialiasing"), 10, (6 + 24 * 7) + (14 * 3)));
		this.buttons.add(new Button(lang.languageValue("gui.selectlang"), 270, 3 + (24 * 3 + (16 + 26 * 2)), 100, 15, 0));
		/*this.buttons.add(new CheckBox("Legacy Crash", 270, 3 + (24 * 3 + (14 + 24 * 2))));
		this.buttons.add(new CheckBox("Legacy Crash", 270, 3 + (24 * 3 + (14 + 24 * 3))));*/
		this.buttons.add(new Button(lang.languageValue("gui.cancel"), 10, DikenEngine.HEIGHT - (1 * 20), 100, 15, 1));
		this.buttons.add(new Button(lang.languageValue("gui.apply"), 10 + 106 * 2, DikenEngine.HEIGHT - (1 * 20), 100, 15, 4));
		this.buttons.add(new Button(lang.languageValue("gui.resetdata"), 10 + 106 * 3, DikenEngine.HEIGHT - (1 * 20), 150, 15, 0xffff0000, 3));
		this.buttons.add(new Button(lang.languageValue("gui.done"), 10 + 106, DikenEngine.HEIGHT - (1 * 20), 100, 15, 2));
		this.addsa = this.buttons.size();
		
		((CheckBox)this.buttons.get(0)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("console")));
		((CheckBox)this.buttons.get(1)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("show_fps")));
		((CheckBox)this.buttons.get(2)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("fullscreen")));
		((CheckBox)this.buttons.get(3)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("sync")));
		((CheckBox)this.buttons.get(5)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("legacy-crash")));
		((CheckBox)this.buttons.get(6)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("debug")));
		((CheckBox)this.buttons.get(7)).setCheck(Boolean.parseBoolean(this.engine.gManager.config.getProperty("antialiasing")));
	}

}
