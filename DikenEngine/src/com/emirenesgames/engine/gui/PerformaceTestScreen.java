package com.emirenesgames.engine.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.Language;
import com.emirenesgames.engine.gui.background.StaticBackground;

public class PerformaceTestScreen extends Screen {
	private Screen parent;
	private Bitmap userImage;

	public PerformaceTestScreen(Screen parent) {
		this.parent = parent;
	}
	
	private int derece;
	public void render(Bitmap screen) {
		super.render(screen);
		screen.draw(userImage, 20, 20);
		screen.draw(userImage.rotate(derece), 20 + userImage.w + 15, 20);
	}

	public void actionListener(int id) {
		if(id == 3) {
			engine.setCurrentScreen(parent);
		}
		
		if(id == 0) {
			this.userImage = this.openFile();
		}
		
		if(id == 1) {
			for(int i = 0; i < userImage.pixels.length; ++i) {
				int alpha = userImage.pixels[i] >> 24 & 0xff;
	        	if (alpha == 0) {
	        		userImage.pixels[i] = 0xff000000;
	            }
	        }
		}
	}
	
	private Bitmap openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(".")); //sets current directory
		fileChooser.setFileFilter(new FileFilter() {

			public boolean accept(File f) {
				if(f.getName().endsWith(".png") || f.getName().endsWith(".jpeg") || f.getName().endsWith(".jpg") || f.getName().endsWith(".gif")) {
					return true;
				}
				return false;
			}

			public String getDescription() {
				return "Resim dosyası";
			}
			
		});
		int response = fileChooser.showOpenDialog(null); //select file to open
		if(response == JFileChooser.APPROVE_OPTION) {
			Bitmap bitmap = Art.load(fileChooser.getSelectedFile().getAbsolutePath(), 1);
			return bitmap;
		}
		return Art.i.icon_x16;

	}
	
	public void tick() {
		super.tick();
		derece++;
	}

	public void openScreen() {
		Language lang = Language.i;
		userImage = Art.i.icon_x16;
		this.setBackground(new StaticBackground(Art.i.bgd_tiles[0][0]));
		this.buttons.clear();
		this.buttons.add(new Button(lang.languageValue("gui.back"), 10, DikenEngine.HEIGHT - (1 * 20), 150, 15, 3));
		this.buttons.add(new Button(lang.languageValue("performance.loadimg"), 10, DikenEngine.HEIGHT - (2 * 20), 150, 15, 0));
		this.buttons.add(new Button(lang.languageValue("performance.imgremovealpha"), 10, DikenEngine.HEIGHT - (3 * 20), 150, 15, 1));
	}

}
