package com.emirenesgames.engine.console;

import java.io.IOException;
import java.io.PrintStream;

import com.emirenesgames.engine.DikenEngine;

public class ConsolePrint extends PrintStream {
	
	private PrintStream systemOut;

	public ConsolePrint(PrintStream stream) {
		super(DikenEngine.consoleOutputStream());
		this.systemOut = stream;
	}

	public void flush() {
		systemOut.flush();
		super.flush();
	}
	
    public void close() {
    	systemOut.close();
		super.close();
	}

	public void write(int b) {
		systemOut.write(b);
		super.write(b);
	}

	public void write(byte[] buf, int off, int len) {
		systemOut.write(buf, off, len);
		super.write(buf, off, len);
	}

	public void write(byte[] buf) throws IOException {
		systemOut.write(buf);
		super.write(buf);
	}

	public void println(String x) {
		Console.println(x);
		super.println(x);
	}
	
	

}
