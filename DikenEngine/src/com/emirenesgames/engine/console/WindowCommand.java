package com.emirenesgames.engine.console;

import java.io.IOException;

import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.gui.window.Window;

public class WindowCommand extends Command {

	public WindowCommand() {
		super("window", "Sanal Pencereli Yönetir");
	}

	public void runCommand(String[] args) {
		DikenEngine engine = DikenEngine.getEngine();
		if(args.length == 0) {
			Console.println("\n window <komut>");
			Console.println(" Komutlar:");
			Console.println("  list");
			Console.println("  closeall");
			Console.println("  close <pencere id>");
			Console.println("  create <x> <y> <pencere adı> <genişlik> <yükseklik>");
			Console.println("  create <class adı>");
			return;
		} else if (args.length == 1) {
			if (args[0].equals("closeall")) {
				engine.wManager.closeAll();
			} else if (args[0].equals("list")) {
				Console.println("Pencere Listesi:");
				for (int i = 0; i < engine.wManager.size(); i++) {
					Console.println(i + " - " + engine.wManager.get(i).title);
				}
			}
		} else if (args.length == 2) {
			if (args[0].equals("close")) {
				int id = Integer.parseInt(args[1]);
				try {
					engine.wManager.get(id).close();
				} catch (IOException e) {
					;
				}
			}
			
			if (args[0].equals("create")) {
				try {
					Class<?> clazz = Class.forName(args[1]);
					engine.wManager.addWindow((Window) clazz.newInstance());
				} catch (Exception e) {
					Console.println("Pencere oluşturulamadı: " + e.getMessage());
				}
			}
		} else if (args.length == 6) {
			if (args[0].equals("create")) {
				int x = Integer.parseInt(args[1]);
				int y = Integer.parseInt(args[2]);
				String title = args[3];
				int width = Integer.parseInt(args[4]);
				int height = Integer.parseInt(args[5]);
				engine.wManager.addWindow(Window.createEmptyWindow(title, x, y, width, height));
			}
		}
	}
}
