package com.emirenesgames.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Language {
	
	public static Language i;
	
	public Map<String, Properties> languageTable = new HashMap<String, Properties>();
	
	public String selectedLanguage = ""; // English
	
	public Language() {
		this.loadLanguage("en-US");
		this.loadLanguage("tr-TR");
	}
	
	static {
		i = new Language();
		i.setDefaultLang("tr-TR");
	}

	public void loadLanguage(String string) {
		Properties lang = new Properties();
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/lang/" + string + ".lang")));
		String a = "";
		try {
			while((a = reader.readLine()) != null) {
				if (a.isBlank())
					continue;
				
				String[] b = a.split("=");
				lang.setProperty(b[0], b[1]);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		languageTable.put(string, lang);
	}

	public void setDefaultLang(String string) {
		if (languageTable.containsKey(string)) {
			selectedLanguage = string;
		}
		
	}
	
	public String languageValue(String key) {
		return languageTable.get(selectedLanguage).getProperty(key, key);
	}
	
	public String languageValue(String key, String lang) {
		if (languageTable.containsKey(lang)) {
			return languageTable.get(lang).getProperty(key, key);
		}
		return key;
	}

}
