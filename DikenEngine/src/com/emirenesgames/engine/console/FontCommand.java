package com.emirenesgames.engine.console;

import com.emirenesgames.engine.gui.UniFont;

public class FontCommand extends Command {

	public FontCommand() {
		super("font");
	}
	
	public void runCommand(String[] args) {
		Console.println("Installed Fonts: ");
		for(int i = 0; i < UniFont.size(); i++) {
			UniFont font = UniFont.getFont(i);
			
			Console.println("FCN: " + font.name + ", FN: " + font.font_name + ", By: " + font.author);
		}
		
	}

}
