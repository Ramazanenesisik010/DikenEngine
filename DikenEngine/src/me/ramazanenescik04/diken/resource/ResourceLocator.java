package me.ramazanenescik04.diken.resource;

import java.util.HashMap;
import java.util.Map;

public class ResourceLocator {
	
	private static Map<String, IResource> resMap = new HashMap<String, IResource>();
	
	public static void addResource(IResource res, String resName) {
		if (res == null) {
			res = IOResource.missingTexture;
		}
		
		resMap.put(resName, res);
	}
	
	public static IResource getResource(String resName) {
		return resMap.getOrDefault(resName, IOResource.missingTexture);
	}
}
