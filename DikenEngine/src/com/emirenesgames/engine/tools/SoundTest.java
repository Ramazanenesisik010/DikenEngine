package com.emirenesgames.engine.tools;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.Language;
import com.emirenesgames.engine.gui.Button;
import com.emirenesgames.engine.gui.Screen;
import com.emirenesgames.engine.gui.Text;
import com.emirenesgames.engine.gui.TextField;
import com.emirenesgames.engine.gui.background.DownBackground;
import com.emirenesgames.engine.sound.WavSound;

public class SoundTest extends Screen {
	
	private Screen parent;
	
	private File file;
	
	private int soundFileType = -1; // wav sound file
	private boolean playing = false;
	
	private Object soundPlayer = null;

	private String currentFrame = "--:--";
	private String endPosition = "--:--";
	
	private TextField tField;

	public SoundTest(Screen screen) {
		this.parent = screen;
	}
	
	public void tick() {
		super.tick();
		
		if(engine.input.keysDown[KeyEvent.VK_ESCAPE]) {
			engine.input.keysDown[KeyEvent.VK_ESCAPE] = false;
			
			if(this.parent != null) {
				engine.setCurrentScreen(parent);
			}
		}
		
		if (this.parent != null) {
			this.buttons.get(3).active = true;
		} else {
			this.buttons.get(3).active = false;
		}
		
		if (!(soundFileType < 0) && playing) {
			this.buttons.get(1).active = false;
			this.buttons.get(2).active = true;
		} else if(!(soundFileType < 0) && !playing){
			this.buttons.get(1).active = true;
			this.buttons.get(2).active = false;
		} else if (soundFileType < 0){
			this.buttons.get(1).active = false;
			this.buttons.get(2).active = false;
		}
		
		if(soundPlayer != null) {
			if(this.soundFileType == 0) {
				WavSound wavSound = ((WavSound)soundPlayer);
				int currentFrame_1 = (int) (wavSound.getFramePosition() / wavSound.getFrameRate());
				int frameLenght_1 = (int) (wavSound.getFrameLenght() / wavSound.getFrameRate());
				currentFrame =  (currentFrame_1 / 60) + ":" + (currentFrame_1 < 10 ? "0" + currentFrame_1 % 60 : currentFrame_1 % 60);
				endPosition = (frameLenght_1 / 60) + ":" + (frameLenght_1 < 10 ? "0" + frameLenght_1 % 60 : frameLenght_1 % 60);
			} else if(this.soundFileType == 1) {
				
			}
		}

	}



	public void render(Bitmap screen) {
		super.render(screen);
		
		Text.render("Konum: ", screen, 2, 2);
		
		String name = "Null", size = "0", isFile = "§ff0000Yanlış", isSoundFile = "§ff0000Yanlış";
		
		if(file != null) {
			name = file.getName();
			if(file.exists()) {
				size = ((file.length()) / 1024) + "";
				isFile = file.isFile() ? "§00ff00Doğru" : "§ff0000Yanlış";
				isSoundFile = (file.isFile() && (file.getName().endsWith("ogg") || file.getName().endsWith("wav"))) ? "§00ff00Doğru" : "§ff0000Yanlış";
			}	
		}
		
		Text.render("Ad: " + name, screen, 2, ((4 + (18 * 2)) + 9));
		Text.render("Boyut: " + size + " KB" , screen, 2, ((4 + 9) * 2) + (18 * 2));
		Text.render("Dosya Mı? " + isFile, screen, 2, ((4 + 9) * 3) + (18 * 2));
		Text.render("Ses Dosyası Mı? " + isSoundFile, screen, 2, ((4 + 9) * 4) + (18 * 2));
		
		Text.render(this.currentFrame + " / " + this.endPosition, screen, 2, (((4 + 9) * 5) + (18 * 2)) + (22 + (6 + 10)));
	}



	public void keyPressed(char var1) {
		file = new File(tField.getText());
		
		if(file.exists() && file.isFile()) {
			if (file.getName().endsWith("ogg")) {
				this.soundFileType = 1;
			} else if(file.getName().endsWith("wav")) {
				this.soundFileType = 0;
			} else {
				this.soundFileType = -1;
			}
		} else {
			this.soundFileType = -1;
		}
	}



	protected void actionListener(int id) {
		if(id == 0) {
			openFile();
		}
		
		if(id == 1) {
			if (this.parent != null) {
				engine.setCurrentScreen(parent);
			}
		}
		if(id == 2) {
			this.playing = true;
			if (soundFileType == 1) {
			} else if(soundFileType == 0) {
				WavSound clip = WavSound.loadSoundFile(file.getAbsolutePath());
				if(clip != null) {
					clip.startSound();
				}
				
				soundPlayer = clip;
			}
		}
		if(id == 3) {
			this.playing = false;
			if (file.getName().endsWith("ogg")) {
				if(this.soundPlayer != null) {
					
				}
			} else if(file.getName().endsWith("wav")) {
				if(this.soundPlayer != null) {
					((WavSound)soundPlayer).stopSound();
				}
				
			}
		}
	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(".")); //sets current directory
		fileChooser.setFileFilter(new FileFilter() {

			public boolean accept(File f) {
				if(f.getName().endsWith(".ogg") || f.getName().endsWith(".wav")) {
					return true;
				}
				return false;
			}

			public String getDescription() {
				return "WAV And OGG sound files";
			}
			
		});
		int response = fileChooser.showOpenDialog(null); //select file to open
		if(response == JFileChooser.APPROVE_OPTION) {
			tField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			file = new File(tField.getText());
			
			if(file.exists() && file.isFile()) {
				if (file.getName().endsWith("ogg")) {
					this.soundFileType = 1;
				} else if(file.getName().endsWith("wav")) {
					this.soundFileType = 0;
				} else {
					this.soundFileType = -1;
				}
			} else {
				this.soundFileType = -1;
			}
		}

	}

	public void openScreen() {
		Language lang = Language.i;
		this.setBackground(new DownBackground(Art.i.bgd_tiles[5][0]));
		this.buttons.clear();
		this.buttons.add(new Button("Aç", (DikenEngine.WIDTH) - (6 + 16), 2 + 18, 16, 16, 0));
		this.buttons.add(new Button("Oynat", 2, (((4 + 9) * 4) + (18 * 2)) + (16 + 9), 100, 16, 2));
		this.buttons.add(new Button("Durdur", 2 + 106, (((4 + 9) * 4) + (18 * 2)) + (16 + 9), 100, 16, 3));
		this.buttons.add(new Button(lang.languageValue("gui.back"), 2, DikenEngine.HEIGHT - 16 - 6, 100, 16, 1));
		tField = new TextField(2, 2 + 18, ((DikenEngine.WIDTH) - 6) - 20, 18, this.engine);
		this.buttons.add(tField);
		if(file != null) {
			tField.setText(file.getAbsolutePath());
			
			if(file.exists() && file.isFile()) {
				if (file.getName().endsWith("ogg")) {
					this.soundFileType = 1;
				} else if(file.getName().endsWith("wav")) {
					this.soundFileType = 0;
				} else {
					this.soundFileType = -1;
				}
			} else {
				this.soundFileType = -1;
			}
		}
	}
	
	public void closeScreen() {
		if(this.soundPlayer != null) {
			if(this.soundFileType == 0) {
				((WavSound)soundPlayer).stopSound();
			} else if (this.soundFileType == 1) {
				
			} else {
				
			}
		}
	}

	public static void main(String[] args) {
		DikenEngine engine =  DikenEngine.initEngine(640, 480 / 2, 2, "SoundTest v0.1 - By Ramazanenescik04");
		engine.startEngine();
		engine.engineWindow.setResizable(true);
		engine.gManager.setMainMenu(new SoundTest(null));
		engine.gManager.openMainMenu();
	}

}
