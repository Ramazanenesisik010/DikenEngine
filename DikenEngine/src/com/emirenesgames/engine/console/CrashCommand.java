package com.emirenesgames.engine.console;

import com.emirenesgames.engine.DikenEngineException;

public class CrashCommand extends Command {

	public CrashCommand() {
		super("crash", "Oyun Motoru Çökertir.");
	}
	
	public void runCommand(String[] args) throws DikenEngineException {
		throw new DikenEngineException("Crash By CrashCommand");
	}

}
