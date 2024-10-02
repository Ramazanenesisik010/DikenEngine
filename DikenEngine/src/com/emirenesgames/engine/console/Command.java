package com.emirenesgames.engine.console;

import org.json.JSONObject;

public class Command {
	
	public static Command[] commands = new Command[Short.MAX_VALUE];
	
	public static void initCommands() {
		commands[0] = new ClearCommand();
		commands[1] = new FontCommand();
	}
	
	public static Command findCommand(String cName) {
		Command cmd = null;
		if (cName.isEmpty())
			return cmd;
		
		for (int i = 0; i < commands.length; i++) {
			Command tmpCmd = commands[i];
			
			if(tmpCmd != null) {
				if (tmpCmd.commandInfo.getString("name").equals(cName)) {
					cmd = tmpCmd;
				}
			}
		}
		
		return cmd;
	}
	
	public void runCommand(String[] args) {
	}
	
	public JSONObject commandInfo;
	
	public Command(String name) {
		this.commandInfo = new JSONObject("{\"name\":\"" + name + "\"}");
	}

}
