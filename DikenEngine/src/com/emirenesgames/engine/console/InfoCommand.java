package com.emirenesgames.engine.console;

public class InfoCommand extends Command {

	public InfoCommand() {
		super("info");
	}

	public void runCommand(String[] args) {
		Console.println("---- Used Libs ----");
		Console.println("- JSON Lib. URL: https://github.com/stleary/JSON-java");
		Console.println("- Dyn4J Physics Lib. URL: https://github.com/dyn4j/dyn4j");
	}
}
