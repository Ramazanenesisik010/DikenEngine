package com.emirenesgames.engine.tools;

import java.util.ArrayList;
import java.util.List;

public class ListTool {
	
	public static boolean isAllObjectIsClass(List l, Class<?> c) {
		for(int i = 0; i < l.size(); i++) {
			if(!(l.get(i).getClass().getSimpleName().equals(c.getSimpleName()))) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> ArrayList<T> toList(T[] array) {
		ArrayList<T> list = new ArrayList<T>();
		list.clear();
		
		for(int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		
		return list;
	}
	
	
}
