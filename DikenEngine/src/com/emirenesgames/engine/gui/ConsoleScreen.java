package com.emirenesgames.engine.gui;

import java.awt.event.KeyEvent;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.console.Command;
import com.emirenesgames.engine.console.Console;

public class ConsoleScreen extends Screen {
	
	private TextField textField;

	public void tick() {
		super.tick();

		textField.tick();
		
		if(engine.input.keysDown[KeyEvent.VK_ENTER] && this.textField.isFocused()) {
			engine.input.keysDown[KeyEvent.VK_ENTER] = false;
			String text = this.textField.getText();
			Command cmd = Command.findCommand(text);
			
			if(cmd != null) {
				cmd.runCommand(null);
			} else {
				if(this.textField.getText().isEmpty()) {
					Console.println("");
				} else {
					Console.println("Bilinmeyen Komut: " + text);
				}
				
			}
			this.textField.setText("");
	    }
	}

	public void render(Bitmap screen) {
		super.render(screen);
		for(int i = 0; i < Console.outputString.size(); i++) {
			Text.render(">" + Console.outputString.get(i), screen, 2, 2 + (i * 9));
			
			if (i == ( 1 + ((DikenEngine.HEIGHT - 9 * 3) / 9))) {
				Console.outputString.remove(0);
			}
		}
		this.textField.render(screen);
	}

	public void actionListener(int id) {
	}
	
	public void keyPressed(char var1) {
		this.textField.keyPressed(var1);
	}

	public void openScreen() {
		this.engine.input.typed = "";
		this.textField = new TextField(2,DikenEngine.HEIGHT-20, DikenEngine.WIDTH - 5, 16);
	}

	public void closeScreen() {
		this.engine.input.typed = "";
	}
	
	

}
