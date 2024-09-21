package com.emirenesgames.engine.console;

public class ClearCommand extends Command {

	public ClearCommand() {
		super("clear");
	}

	public void runCommand(String[] args) {
		Console.outputString.clear();
	}
}
