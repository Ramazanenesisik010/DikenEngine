package me.ramazanenescik04.diken.gui.compoment;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.UniFont;
import me.ramazanenescik04.diken.resource.Bitmap;

public class Text extends GuiCompoment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String text;
	public int color;
	public UniFont font;

	public Text(String text, int x, int y) {
		this(text, x, y, 0xFFFFFFFF, DikenEngine.getEngine().defaultFont);
	}
	
	public Text(String text, int x, int y, UniFont font) {
		this(text, x, y, 0xFFFFFFFF, font);
	}
	
	public Text(String text, int x, int y, int color, UniFont font) {
		super(x, y, Text.stringBitmapWidth(text, DikenEngine.getEngine().defaultFont), Text.stringBitmapAverageHeight(text, DikenEngine.getEngine().defaultFont));
		this.text = text;
		this.color = color;
		this.font = font;
	}
	
	public Text(String text, int x, int y, int color) {
		this(text, x, y, color, DikenEngine.getEngine().defaultFont);
	}
	
	public Bitmap render() {
		Bitmap bitmap = super.render();
		Text.render(text, bitmap, 0, 0, color, font);
		return bitmap;
	}
	
	@Override
	public void tick(DikenEngine engine) {
		if((this.width != Text.stringBitmapWidth(text, font))) {
			this.width = Text.stringBitmapWidth(text, font);
		}
		
		if((this.height != Text.stringBitmapAverageHeight(text, DikenEngine.getEngine().defaultFont))) {
			this.height = Text.stringBitmapAverageHeight(text, font);
		}
	}

	public static void render(String text, Bitmap bitmap, int x, int y, int color, UniFont font) {
	    // Split the text into lines
	    String[] lines = text.split("\n");
	    
	    // Track the current y-position for rendering
	    int currentY = y;
	    
	    // Height of a single line (approximate)
	    int lineHeight = stringBitmapAverageHeight(text, font) + 2; // Add a small padding
	    
	    for (String line : lines) {
	        Bitmap[] chars = UniFont.getBitmapChars(line, font);
	        int w = 0;
	        
	        for (int i = 0; i < chars.length; i++) {
	            Bitmap btp = chars[i];
	            
	            // Color code handling
	            if (btp == UniFont.getBitmapChar('§', font) && i + 6 < line.length()) {
	                String colorCode = line.substring(i + 1, i + 7);
	                color = (int) Long.parseLong(colorCode, 16);
	                i += 6;
	                continue;
	            }
	            
	            int darkColor = 0, var6;
	            var6 = darkColor & -16777216;
	            darkColor = (color & 16579836) >> 2;
	            darkColor += var6;
	            
	            // Render character with shadow
	            bitmap.blendDraw(btp, (x + w) + 1, currentY + 1, darkColor);
	            bitmap.blendDraw(btp, x + w, currentY, color);
	            
	            w += ((btp.w) + 1);
	        }
	        
	        // Move to the next line
	        currentY += lineHeight;
	    }
	}
	
	public static void render(String text, Bitmap bitmap, int x, int y, int color) {
		render(text, bitmap, x, y, color, DikenEngine.getEngine().defaultFont);
	}
	
	public static void render(String text, Bitmap bitmap, int x, int y, UniFont font) {
		render(text, bitmap, x, y, 0xffffffff, font);
	}
	
	public static void render(String text, Bitmap bitmap, int x, int y) {
		render(text, bitmap, x, y, 0xffffffff, DikenEngine.getEngine().defaultFont);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y) {
		renderCenter(string, bitmap, x, y, 0xffffffff, DikenEngine.getEngine().defaultFont);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y, int color) {
		renderCenter(string, bitmap, x, y, color, DikenEngine.getEngine().defaultFont);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y, UniFont font) {
		renderCenter(string, bitmap, x, y, 0xffffffff, font);
	}
	
	public static void renderCenter(String string, Bitmap bitmap, int x, int y, int color, UniFont font) {
		int x1 = x - (string.length() * 6 / 2);
		
		render(string, bitmap,x1, y, color, font);
	}
	
	public static int stringBitmapWidth(String text, UniFont font) {
		Bitmap[] chars = UniFont.getBitmapChars(text, font);
		int w = 0;
		for (int i = 0; i < chars.length; i++) {
			Bitmap btp = chars[i];
			w += ((btp.w) + 1);
		}
		
		return w;
	}
	
	public static int stringBitmapAverageWidth(String[] texts, UniFont font) {
		int w = 0;
		int ah = 0;
		for(int l = 0; l < texts.length; l++) {
			String text = texts[l];
			
			Bitmap[] chars = UniFont.getBitmapChars(text, font);
			
			for (int i = 0; i < chars.length; i++) {
				Bitmap btp = chars[i];
				ah += ((btp.w) + 1);
			}
		}
		
		w = ah / texts.length;
				
		return w;
	}
	
	public static int stringBitmapAverageHeight(String text, UniFont font) {
		Bitmap[] chars = UniFont.getBitmapChars(text, font);
		int h = 0;
		int ah = 0;
		for (int i = 0; i < chars.length; i++) {
			Bitmap btp = chars[i];
			ah += ((btp.h) + 1);
		}
		
		if(chars.length <= 0) {
			return 0;
		}
		
		h = ah / chars.length;
		
		return h;
	}
}
