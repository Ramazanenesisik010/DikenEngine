package com.emirenesgames.engine.gui;

import java.io.*;
import java.util.*;

import org.json.*;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;

public class UniFont {
	
	private static List<UniFont> unifonts = new ArrayList<UniFont>();
	
	public String charTypes = "";
	public String author, font_name;
	
	public Bitmap[] charBitmaps;
	
	public String name;
	
	public static void createFont(String fontName) {
		UniFont font = new UniFont();
		
		Bitmap bitmap = Art.load("/fonts/" + fontName + "/font_bitmap.png", Art.CLASS_RESOURCE_LOAD);
		font.name = fontName;
		BufferedReader reader = new BufferedReader(new InputStreamReader(UniFont.class.getResourceAsStream("/fonts/" + fontName + "/font_data.json")));
		String data = "",data2 = "";
		
		try {
			while((data = reader.readLine()) != null) {
				data2 = data2 + data;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject obj = new JSONObject(data2);
		font.font_name = obj.getString("font_name");
		font.author = obj.getString("author");
		
		JSONArray array = obj.getJSONArray("chars");
		
		font.charBitmaps = new Bitmap[array.length()];
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj1 = array.getJSONObject(i);
			font.charTypes = font.charTypes + obj1.getString("char");
		    font.charBitmaps[i] = new Bitmap(obj1.getInt("width"), obj1.getInt("height"));
		    
		    if (obj1.getInt("x") < 0 || obj1.getInt("y") < 0) {
    			continue;
    		}
		    
		    for(int x = 0; x < obj1.getInt("width"); x++) {
		    	for(int y = 0; y < obj1.getInt("height"); y++) {
		    		
		    		int color = bitmap.pixels[(x + obj1.getInt("x")) + (y + obj1.getInt("y")) * bitmap.w];
		    		
		    		font.charBitmaps[i].setPixel(x, y, color);
			    }
		    }
		    
		    
		}
		
		System.out.println("Loaded Font: " + fontName);
		System.out.println("Font Types: " + font.charTypes);
		
		unifonts.add(font);
	}
	
	public static boolean removeFont(String s) {
        UniFont font = getFont(s);
        if(font == null) {
        	return false;
        }
		unifonts.remove(font);
		return true;
	}
	
	public static UniFont getFont(String name) {
		UniFont font = null;
		
		for (int i = 0; i < unifonts.size(); i++) {
			UniFont tmpFont = unifonts.get(i);
			
			if(tmpFont.name.equals(name)) {
				font = tmpFont;
			}
		}
		
		return font;
	}
	
	public static int size() {
		return unifonts.size();
	}
	
	public static Bitmap[] getBitmapChars(String text, UniFont font) {
		List<Bitmap> list = new ArrayList<Bitmap>();
		for (int i = 0; i < text.length(); i++) {
			int ch = font.charTypes.indexOf(text.charAt(i));
			if (ch < 0) continue;
			if (ch > font.charBitmaps.length) continue;
			list.add(font.charBitmaps[ch]);
		}
		return list.toArray(new Bitmap[] {});
	}
	
	public static Bitmap getBitmapChar(char chara, UniFont font) {
		int ch = font.charTypes.indexOf(chara);
		if (ch < 0) return new Bitmap(6, 8);
		if (ch > font.charBitmaps.length) return new Bitmap(6, 8);
		return font.charBitmaps[ch];
	}
	
	public static UniFont getFont(int id) {
		UniFont font = unifonts.get(id);
		return font;
	}

}
