package com.emirenesgames.engine.console;

import java.util.*;

public class Console {
	
	public static List<String> outputString = new ArrayList<String>();
	
	public static void println(String string) {
		outputString.add(string);
	}

}
