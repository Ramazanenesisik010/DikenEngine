package com.emirenesgames.engine.console;

import java.io.File;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;

public class ImageToTextCommand extends Command {

	public ImageToTextCommand() {
		super("imgtotext", "Resmi yazıya döker.");
	}

	public void runCommand(String[] args) {
		if(args.length == 1) {
			File file = new File(args[0]);
			if(file.exists() || file.isFile()) {
				Console.println("Çıktı: ");
				Bitmap bitmap = Art.load(args[0], Art.NORMAL_RESOURCE_LOAD);
				
				for(int y = 0; y < bitmap.h; y++) {
					String color = "";
					for(int x = 0; x < bitmap.w; x++) {
						int bColor = bitmap.pixels[x + y * bitmap.w];
						
						if(bColor == 0 || bColor == 0x00000000) {
							color += "§000000■";
						} else {
							color += "§" + Integer.toHexString(bColor).substring(2) + "■";
						}
					}
					Console.println(color);
				}
				
				
			} else {
				Console.println("Geçersiz Dosya: " + args[0]);
			}
			
		} else {
			Console.println("Komut Hatalı!");
			Console.println("-> imgtotext [path]");
		}	
		
	}
	
	

}
