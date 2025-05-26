package me.ramazanenescik04.diken.resource;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.ramazanenescik04.diken.SoundManager;

public class IOResource {
	
	public static final Bitmap missingTexture = generateMissingTexture();
	
	public static IResource loadResource(InputStream stream, EnumResource _enum) {
		if (_enum == EnumResource.IMAGE) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(stream);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toBitmap(img);
		} else if (_enum == EnumResource.SOUND) {
			return SoundManager.loadSound(stream);
		} else if (_enum == EnumResource.CURSOR) {
			Bitmap cursorBitmap = (Bitmap) loadResource(stream, EnumResource.IMAGE);
			CursorResource res = new CursorResource();
			res.cursorBitmap = cursorBitmap;
			
			return res;
		}
		return null;
	}
	
	public static Bitmap[][] loadResourceAndCut(InputStream stream, int sw, int sh) {
		BufferedImage img = ((Bitmap)loadResource(stream, EnumResource.IMAGE)).toImage();
		int xSlices = img.getWidth() / sw;
	    int ySlices = img.getHeight() / sh;
	    Bitmap[][] result = new Bitmap[xSlices][ySlices];

	    for(int x = 0; x < xSlices; ++x) {
	    	for(int y = 0; y < ySlices; ++y) {
	        	result[x][y] = new Bitmap(sw, sh);
	        	img.getRGB(x * sw, y * sh, sw, sh, result[x][y].pixels, 0, sw);
	        }
	    }

	    return result;
	}
	
	public static InputStream createClassResourceStream(String path) {
		InputStream stream = IOResource.class.getResourceAsStream(path);
		if (stream == null) {
			System.err.println("Error: Resource not found: " + path);
		}
		return stream;
	}
	
	public static InputStream createFileStream(String path) {
		InputStream stream;
		try {
			stream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			stream = null;
			e.printStackTrace();
		}
		if (stream == null) {
			System.err.println("Error: Resource not found: " + path);
		}
		return stream;
	}
	
	private static Bitmap generateMissingTexture() {
		Bitmap bitmap = new Bitmap(16, 16);
		bitmap.fill(0, 0, 15, 15, 0xff000000);
		bitmap.fill(0, 8, 7, 15, 0xff76428a);
		bitmap.fill(8, 0, 15, 7, 0xff76428a);
		return bitmap;
	}
	
	public static Bitmap toBitmap(BufferedImage img) {
		if (img == null) {
			img = missingTexture.toImage();
		}
		   
		int sw = img.getWidth();
		int sh = img.getHeight();
		Bitmap result = new Bitmap(sw, sh);
		img.getRGB(0, 0, sw, sh, result.pixels, 0, sw);
		return result;
	}

}
