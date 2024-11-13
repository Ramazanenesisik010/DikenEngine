package com.emirenesgames.engine.console;

import com.emirenesgames.engine.DikenEngine;

public class SetCommand extends Command {

	public SetCommand() {
		super("set", "Verileri Değişiklik Yapar.");
	}
	
	public void runCommand(String[] args) {
		if(args.length == 2) {
			String key = args[0];
			if(DikenEngine.getEngine().gManager.config.containsKey(key)) {
				Console.println("Veri Değiştirildi!");
			} else {
				Console.println("Başarılıyla Oluşturuldu.");
			}
			DikenEngine.getEngine().gManager.config.setProperty(args[0], args[1]);
		} else if (args.length == 1){
			String key = args[0];
			if(DikenEngine.getEngine().gManager.config.containsKey(key)) {
				Console.println("Key Geçerli.");
			} else {
				Console.println("Key Bulunamadı. Lütfen Oluşturunuz.");
			}
		} else {
			Console.println("Komut Hatalı! Lütfen Bunları Deneyiniz.");
			Console.println("set [key] [value] - Veri Değiştirir veya oluşturur.");
			Console.println("veya");
			Console.println("set [key] - Key Kontrol Eder.");
		}
		
	}

}
