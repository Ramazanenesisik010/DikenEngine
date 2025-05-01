package me.ramazanenescik04.diken.tools;

import me.ramazanenescik04.diken.DikenEngine;

public class HTTPGet {
	
	public static String get(String url) {
		StringBuilder result = new StringBuilder();
		try {
			java.net.URL urlObj = new java.net.URL(url);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			conn.connect();
			DikenEngine.log("HTTPGet: Connected to URL: " + url);
			
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			DikenEngine.log("HTTPGet: ERROR: " + e.getMessage());
		}
		return result.toString();
	}

}
