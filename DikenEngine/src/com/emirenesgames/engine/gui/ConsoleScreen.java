package com.emirenesgames.engine.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.DikenEngineException;
import com.emirenesgames.engine.console.Command;
import com.emirenesgames.engine.console.Console;
import com.emirenesgames.engine.gui.background.StaticBackground;

public class ConsoleScreen extends Screen {
	
	private TextField textField;
	private Screen parent;
	
	public ConsoleScreen(Screen parent) {
		this.parent = parent;
	}

	public void tick() {
		super.tick();

		textField.tick();
		
		if(engine.input.keysDown[KeyEvent.VK_ENTER] && this.textField.isFocused()) {
			engine.input.keysDown[KeyEvent.VK_ENTER] = false;
			sendCommand();
	    }
		
		if(engine.input.keysDown[KeyEvent.VK_ESCAPE]) {
			engine.input.keysDown[KeyEvent.VK_ESCAPE] = false;
			engine.setCurrentScreen(parent);
		}
	}

	public void render(Bitmap screen) {
		super.render(screen);
		for(int i = 0; i < Console.outputString.size(); i++) {
			String string = Console.outputString.get(i);
			screen.fill(1, 1 + (i * Text.stringBitmapAverageHeight(string, engine.defaultFont)),
					1 + Text.stringBitmapWidth(string, engine.defaultFont),
					1 + (i * Text.stringBitmapAverageHeight(string, engine.defaultFont) + Text.stringBitmapAverageHeight(string, engine.defaultFont)),
					0xff000000);
			Text.render(string, screen, 2, 2 + (i * Text.stringBitmapAverageHeight(string, engine.defaultFont)));
			
			if (i == ( 1 + ((DikenEngine.HEIGHT - 9 * 3) / 9))) {
				Console.outputString.remove(0);
			}
		}
		this.textField.render(screen);
	}

	public void actionListener(int id) {
		if (id == 0) {
			sendCommand();
		}
		if (id == 1) {
			engine.setCurrentScreen(parent);
		}
	}
	
	private void sendCommand() {
		String text = this.textField.getText();
		String[] a = text.split(" ");
		a[0] = a[0].toLowerCase(Locale.ROOT);
		Command cmd;
		try {
			cmd = Command.findCommand(a[0]);
		} catch (DikenEngineException e) {
			cmd = null;
			e.printStackTrace();
		}
		
		Console.println(">" + text);
		
		if(cmd != null) {	
			String[] args = new String[a.length - 1];
			for(int i = 0; i < args.length; i++) {
				args[i] = a[i + 1];
			}
			try {
				cmd.runCommand(args);
			} catch (DikenEngineException e) {
				e.printStackTrace();
				this.engine.crashScreen(e);
			}
		} else {
			if(this.textField.getText().isEmpty()) {
				Console.println("");
			} else {
				Console.println("Bilinmeyen Komut: " + text + ", Lütfen \"help\" yazınız.");
			}
			
		}
		Console.println("");
		this.textField.setText("");
	}
	
	public void keyPressed(char var1) {
		this.textField.keyPressed(var1);
	}

	public void openScreen() {
		this.setBackground(new StaticBackground(Art.i.bgd_tiles[4][0]));
		this.buttons.clear();
		this.buttons.add(new Button("Gönder", (DikenEngine.WIDTH - 5) - 50, DikenEngine.HEIGHT - 21, 50, 16, 0));
		this.buttons.add(new Button("Geri Dön", (DikenEngine.WIDTH - 5) - (50 * 2), DikenEngine.HEIGHT - 21, 50, 16, 1));
		this.engine.input.typed = "";
		this.textField = new TextField(2,DikenEngine.HEIGHT-20, (DikenEngine.WIDTH - 10) - (50 * 2), 16, this.engine);
		
		if(DikenEngine.SCALE >= 3) {
			Console.println("Uyarı: Bazı yazılar sığmayabilir.");
		}
	}

	public void closeScreen() {
		this.engine.input.typed = "";
	}
	
	

}
