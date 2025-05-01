package me.ramazanenescik04.diken.resource;

import java.awt.image.*;
import java.util.*;

public class ArrayBitmap implements IResource {
	private static final long serialVersionUID = 1L;
	
	public Bitmap[][] bitmap;

	@Override
	public EnumResource getResourceType() {
		return EnumResource.IMAGE;
	}
}
