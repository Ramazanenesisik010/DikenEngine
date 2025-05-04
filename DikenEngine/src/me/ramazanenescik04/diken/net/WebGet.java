package me.ramazanenescik04.diken.net;

import java.net.*;
import javax.net.ssl.*;

import me.ramazanenescik04.diken.DikenEngine;

public class WebGet {
	
	public static String getHTTP(String url) {
		StringBuilder result = new StringBuilder();
		try {
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
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
			DikenEngine.log("HTTPGet-HTTP: ERROR: " + e.getMessage());
		}
		return result.toString();
	}
	
	public static String getHTTPS(String url) {
		return getHTTPS(url, HttpsURLConnection.getDefaultSSLSocketFactory());
	}
	
	public static String getHTTPS(String url, SSLSocketFactory cerf) {
		StringBuilder result = new StringBuilder();
		try {
			URL urlObj = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
			conn.setSSLSocketFactory(cerf);
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
			DikenEngine.log("WebGet-HTTPS: ERROR: " + e.getMessage());
		}
		DikenEngine.log("WebGet-HTTPS: " + result.toString());
		return result.toString();
	}

}
