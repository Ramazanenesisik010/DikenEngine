package com.emirenesgames.engine.console;

import com.emirenesgames.engine.gui.UniFont;

public class FontCommand extends Command {

	public FontCommand() {
		super("font", "Yazı Tipi Komutla Ekleyip Silmenize Sağlar.");
	}
	
	public void runCommand(String[] args) {
		if(args.length == 2) {
			String command = args[0];
			String value = args[1];
			
			if(command.equals("create")) {
				UniFont.createFont(value);
				Console.println("Başarıyla Oluşturuldu: " + value);
			} else if(command.equals("remove")) {
				
				Console.println((UniFont.removeFont(value) ? "Başarıyla Silindi: " : "Silinemedi: ") + value);
			} else {
				Console.println("Bilimeyen Komut: " + command);
			}
		} else {
			Console.println("Kurulu Yazı Tipiler: ");
			for(int i = 0; i < UniFont.size(); i++) {
				UniFont font = UniFont.getFont(i);
				
				Console.println("YTKA: " + font.name + ", YTA: " + font.font_name + ", Yapan: " + font.author);
			}
		}
	}

}
