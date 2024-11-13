package com.emirenesgames.engine.console;

public class ClearCommand extends Command {

	public ClearCommand() {
		super("clear", "Ekranı Temizler.");
	}

	public void runCommand(String[] args) {
		Console.outputString.clear();
	}
}
