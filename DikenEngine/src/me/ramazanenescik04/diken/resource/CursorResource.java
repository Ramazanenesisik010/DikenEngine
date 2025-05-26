package me.ramazanenescik04.diken.resource;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

public class CursorResource implements IResource {
	private static final long serialVersionUID = 1L;
	public Bitmap cursorBitmap;
	private Cursor cursor;
	
	private Cursor generateCursor() {
		if (cursorBitmap == null)
			cursorBitmap = IOResource.missingTexture;
		
		IntBuffer buffer = BufferUtils.createIntBuffer(cursorBitmap.pixels.length);
		
		// Buffer'ı hazırla
		buffer.clear();
        
		int width = cursorBitmap.w;
		int height = cursorBitmap.h;
		int[] pixels = cursorBitmap.pixels;
		for (int y = 0; y < height; y++) {
		    for (int x = 0; x < width; x++) {
		        int mirroredX = width - 1 - x;
		        int color = pixels[y * width + mirroredX];
		        buffer.put(color);
		    }
		}

        
        // Buffer'ı okuma için hazırla
        buffer.flip();
		
        Cursor cursor = null;
		try {
			cursor = new Cursor(cursorBitmap.w, cursorBitmap.h, 2, cursorBitmap.h - 2, 1, buffer, null);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		return cursor;
	}
	
	public Cursor getCursor() {
		if (cursor == null) {
			cursor = generateCursor();
		}
		
		return cursor;
	}

	public EnumResource getResourceType() {
		return EnumResource.CURSOR;
	}

}
