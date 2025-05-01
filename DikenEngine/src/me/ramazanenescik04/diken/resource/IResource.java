package me.ramazanenescik04.diken.resource;

import java.io.*;

public interface IResource extends java.io.Serializable {
	public EnumResource getResourceType();
	
	default boolean resourceIs(EnumResource res) {
		EnumResource thisRes = this.getResourceType();
		
		if(thisRes == res) return true; else return false;
	}
	
	public default void saveResource(OutputStream stream) {
	}
}
