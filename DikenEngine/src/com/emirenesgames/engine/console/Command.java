package com.emirenesgames.engine.console;

import org.json.JSONObject;

import com.emirenesgames.engine.DikenEngineException;

public abstract class Command {
	
	public static Command[] commands = new Command[Short.MAX_VALUE];
	
	public static void initCommands() {
		commands[0] = new ClearCommand();
		commands[1] = new FontCommand();
		commands[2] = new HelpCommand();
		commands[3] = new SetCommand();
		commands[4] = new SayCommand();
		commands[5] = new CrashCommand();
		commands[6] = new ImageToTextCommand();
		commands[7] = new GetCommand();
		commands[8] = new DeleteCommand();
	}
	
	public static Command findCommand(String cName) throws DikenEngineException {
		Command cmd = null;
		if (cName.isEmpty())
			return cmd;
		
		for (int i = 0; i < commands.length; i++) {
			Command tmpCmd = commands[i];
			
			if(tmpCmd != null) {
				if(tmpCmd.commandInfo == null) {
					throw new DikenEngineException("JSON null");
				}
				if (cName.equals(tmpCmd.commandInfo.getString("name"))) {
					cmd = tmpCmd;
				}
			}
		}
		
		return cmd;
	}
	
	public void runCommand(String[] args) throws DikenEngineException {
	}
	
	public JSONObject commandInfo;
	
	public Command(String name, String helpMessage) {
		this.commandInfo = new JSONObject("{\"name\":\"" + name + "\", \"helpMessage\":\"" + helpMessage + "\"}");
	}

}
