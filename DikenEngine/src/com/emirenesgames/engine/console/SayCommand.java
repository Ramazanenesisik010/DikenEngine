package com.emirenesgames.engine.console;

public class SayCommand extends Command {

	public SayCommand() {
		super("say", "Mesajı Komuta Yazdırır.");
	}
	
	public void runCommand(String[] args) {
		if(args.length > 0) {
			String message = "";
			for(String m : args) {
				message = message + (m + " ");
			}
			Console.println(message);
		} else {
			Console.println("Komut Hatalı. Lütfen Bunu Deneyin: ");
			Console.println("say [message]");
		}
		
	}

}
