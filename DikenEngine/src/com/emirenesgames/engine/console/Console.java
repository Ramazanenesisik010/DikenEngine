package com.emirenesgames.engine.console;

import java.util.*;

public class Console {
	
	public static List<String> outputString = new ArrayList<String>();
	
	public static void println(String string) {
		outputString.add(string);
	}
	
	public static void println(Object object) {
		outputString.add(object.toString());
	}
	
	public static void println(int object) {
		outputString.add(object + "");
	}
	
	public static void println(long object) {
		outputString.add(object + "");
	}
	
	public static void println(float object) {
		outputString.add(object + "");
	}
	
	public static void println(double object) {
		outputString.add(object + "");
	}
	
	public static void println(short object) {
		outputString.add(object + "");
	}

}
