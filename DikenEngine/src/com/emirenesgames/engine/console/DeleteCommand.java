package com.emirenesgames.engine.console;

import com.emirenesgames.engine.DikenEngine;

public class DeleteCommand extends Command {

	public DeleteCommand() {
		super("delete", "Veriyi Siler.");
	}
	
	public void runCommand(String[] args) {if (args.length == 1){
			String key = args[0];
			if(DikenEngine.getEngine().gManager.config.containsKey(key)) {
				DikenEngine.getEngine().gManager.config.remove(key);
				Console.println("Key Başarıyla Silindi: " + key);
			} else {
				Console.println("Key Silinemedi: " + key);
			}
		} else {
			Console.println("Komut Hatalı! Lütfen Bunları Deneyiniz.");
			Console.println("delete [key] - Veriyi Siler.");
		}
		
	}

}
