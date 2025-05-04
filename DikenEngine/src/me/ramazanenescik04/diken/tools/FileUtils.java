package me.ramazanenescik04.diken.tools;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
	
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
	
}
