package com.emirenesgames.engine.console;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help", "Bu Komutu Gösterir.");
	}

	public void runCommand(String[] args) {
		Console.println("-=-=-=-=-=- Komutlar -=-=-=-=-=-");
		for(int i = 0; i < Command.commands.length; i++) {
			Command c = Command.commands[i];
			if(c != null) {
				Console.println((i + 1) + ". " + c.commandInfo.getString("name") + " - " + c.commandInfo.getString("helpMessage"));
			}
		}
	}
}
