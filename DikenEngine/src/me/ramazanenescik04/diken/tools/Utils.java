package me.ramazanenescik04.diken.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class Utils {
	public static String[] readFileArray(File file) {
		if (!file.exists()) {
			return new String[] {""};
		}
		
		try {
			BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			ArrayList<String> list = new ArrayList<>();
			String value = "";
			while((value = d.readLine()) != null) {
				list.add(value);
			}
			value = "";
			d.close();
			
			return list.toArray(new String[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new String[] {""};
	}
	
	public static String readFile(File file) {
		String str = "";
		String[] array = readFileArray(file);
	
		for (int i = 0; i < array.length; i++) {
			str += array[i];
		}
		
		return str;
	}
	
	public static <T> void writeFileArray(File file, T[] array) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter d = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			for (T var : array) {
				d.write(var.toString());
			}
			d.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getStackTraceStringArray(Throwable _throw) {
		StringWriter writer = new StringWriter();
		_throw.printStackTrace(new PrintWriter(writer));
		
		return writer.toString().lines().toArray(String[]::new);
	}
	
	public static String getStackTraceString(Throwable _throw) {
		StringWriter writer = new StringWriter();
		_throw.printStackTrace(new PrintWriter(writer));
		
		return writer.toString();
	}
	
	public static long timeToLong(int... time) {
		if (time.length == 0) {
			return 0;
		}
		
		long total = 0;
		int multiplier = 1;
		
		for (int i = time.length - 1; i >= 0; i--) {
			total += time[i] * multiplier;
			multiplier *= 60;
		}
		
		return total * 1000; // Convert to milliseconds
	}
	
	// currentTime ve maxTime, System.currentTimeMillis() gibi değerler olamaz!
	public static int toProccesBarValue(long currentTime, long maxTime, int maxValue) {
		if (maxTime <= 0) {
			return 0;
		}
		
		double percentage = (double) currentTime / maxTime;
		return (int) (percentage * maxValue);
	}
}
