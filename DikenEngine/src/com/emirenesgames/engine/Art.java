package com.emirenesgames.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Art {
   public static Art i;
   
   public static final int CLASS_RESOURCE_LOAD = 0;
   public static final int NORMAL_RESOURCE_LOAD = 1;
  
   public Bitmap[][] cursors = loadAndCut("/cursors.png", 16, 16, Art.CLASS_RESOURCE_LOAD);
   public Bitmap[][] button = loadAndCut("/button.png", 16, 16, Art.CLASS_RESOURCE_LOAD);
   public Bitmap[][] bgd_tiles = loadAndCut("/background_tiles.png", 32, 32, Art.CLASS_RESOURCE_LOAD);
   public Bitmap[][] check_box = loadAndCut("/check_box.png", 20, 20, Art.CLASS_RESOURCE_LOAD);
   public Bitmap icon_x16 = load("/icon-x16.png");
   public Bitmap[][] default_win_icons = loadAndCut("/win_icons.png", 16, 16);

   public Bitmap[][] se_button = loadAndCut("/screen_editor_buttons.png", 16, 16, Art.CLASS_RESOURCE_LOAD);

   public static void init() {
      i = new Art();
   }

   public static Bitmap[][] loadAndCut(String name, int sw, int sh, int loadId) {
      BufferedImage img;
      try {
    	  if(loadId == Art.CLASS_RESOURCE_LOAD) {
    		  img = ImageIO.read(Art.class.getResource(name));
    	  } else if (loadId == Art.NORMAL_RESOURCE_LOAD) {
    		  img = ImageIO.read(new File(name));
    	  } else {
    		  img = ImageIO.read(Art.class.getResource(name));
    	  }
      } catch (IOException var9) {
         throw new RuntimeException("Failed to load " + name);
      }

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

   public static Bitmap load(String name, int loadId) {
      BufferedImage img;
      
      try {
    	  if(loadId == Art.CLASS_RESOURCE_LOAD) {
    		  img = ImageIO.read(Art.class.getResource(name));
    	  } else if (loadId == Art.NORMAL_RESOURCE_LOAD) {
    		  img = ImageIO.read(new File(name));
    	  } else {
    		  img = ImageIO.read(Art.class.getResource(name));
    	  }
        
      } catch (IOException var5) {
         throw new RuntimeException("Failed to load " + name);
      }

      int sw = img.getWidth();
      int sh = img.getHeight();
      Bitmap result = new Bitmap(sw, sh);
      img.getRGB(0, 0, sw, sh, result.pixels, 0, sw);
      return result;
   }
   
   public static Bitmap[][] loadAndCut(String name, int sw, int sh) {
	   return loadAndCut(name, sw, sh, Art.CLASS_RESOURCE_LOAD);
   }

   public static Bitmap load(String name) {
	   return load(name, Art.CLASS_RESOURCE_LOAD);
   }

   public static Bitmap[][] recolor(Bitmap[][] bitmaps, int a0, int b0, int a1, int b1) {
      for(int x = 0; x < bitmaps.length; ++x) {
         for(int y = 0; y < bitmaps[x].length; ++y) {
            Bitmap bm = bitmaps[x][y];

            for(int i = 0; i < bm.pixels.length; ++i) {
               if (bm.pixels[i] == a0) {
                  bm.pixels[i] = b0;
               }

               if (bm.pixels[i] == a1) {
                  bm.pixels[i] = b1;
               }
            }
         }
      }

      return bitmaps;
   }

   public static Bitmap[][] recolor(Bitmap[][] bitmaps, int a0, int b0) {
      for(int x = 0; x < bitmaps.length; ++x) {
         for(int y = 0; y < bitmaps[x].length; ++y) {
            Bitmap bm = bitmaps[x][y];

            for(int i = 0; i < bm.pixels.length; ++i) {
               if (bm.pixels[i] == a0) {
                  bm.pixels[i] = b0;
               }
            }
         }
      }

      return bitmaps;
   }
   
   public static Bitmap toBitmap(BufferedImage img) {
	   if (img == null) {
		   return null;
	   }
	   
	   int sw = img.getWidth();
	   int sh = img.getHeight();
	   Bitmap result = new Bitmap(sw, sh);
	   img.getRGB(0, 0, sw, sh, result.pixels, 0, sw);
	   return result;
   }
}
