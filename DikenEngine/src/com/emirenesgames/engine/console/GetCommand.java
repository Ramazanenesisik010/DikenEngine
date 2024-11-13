package com.emirenesgames.engine.console;

import com.emirenesgames.engine.DikenEngine;

public class GetCommand extends Command {

	public GetCommand() {
		super("get", "Veriyi Verir.");
	}
	
	public void runCommand(String[] args) {
		if(args.length == 1) {
			String value = DikenEngine.getEngine().gManager.config.getProperty(args[0]);
			if(value != null) {
				Console.println("Değer: " + value);
			} else {
				Console.println("Key Geçersiz");
			}
		} else {
			Console.println("Komut Hatalı!");
			Console.println("-> get [key]");
		}
	}

}
