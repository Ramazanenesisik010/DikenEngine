package com.emirenesgames.engine.console;

import com.emirenesgames.engine.DikenEngine;
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
			} else if(command.equals("setDefaultFont")) {	
				UniFont font = UniFont.getFont(value);
				if(font != null) {
					DikenEngine.getEngine().defaultFont = font;
					Console.println("Başarıyla Değiştirildi: " + value);
				} else {
					Console.println("Böyle Font Bulunamadı: " + value);
				}
			} else {
				Console.println("Bilimeyen Komut: " + command);
			}
		} else if (args.length == 1) {
			String command = args[0];
			
			if(command.equals("list")) {
				Console.println("Kurulu Yazı Tipiler: ");
				Console.println("");
				for(int i = 0; i < UniFont.size(); i++) {
					UniFont font = UniFont.getFont(i);
					
					Console.println("ID: " + i + ",Yazı Tipi Adı: " + font.name + ", Yazı Tipi Detaylı Adı: " + font.font_name);
				}
			
		    } else {
				Console.println("Bilimeyen Komut: " + command);
			}
		
	    } else {
			Console.println("Hatalı Komut!");
			Console.println("Kullanımı: ");
			Console.println("");
			Console.println("font create [fontName] - Yeni yazıtipi oluşturur.");
			Console.println("font delete [fontName] - Yazıtipini siler.");
			Console.println("font setDefaultFont [fontName] - Varsayılan yazıtipini değiştirir..");
			Console.println("font list - Yazıtipleri listeler.");
		}
	}

}
