package com.emirenesgames.engine;

import com.emirenesgames.engine.console.Command;
import com.emirenesgames.engine.gui.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.*;

import javax.swing.JFrame;

public class DikenEngine extends Canvas implements Runnable {
   private static final long serialVersionUID = 1L;
   public static int WIDTH = 320;
   public static int HEIGHT = 240;
   public static int SCALE = 3;
   
   private static DikenEngine localEngine;
   public static JFrame frame = new JFrame("Diken Engine");
   private static String[] staticArgs = null;
   
   private boolean keepRunning = true;
   
   private BufferedImage screenImage;
   public Bitmap screenBitmap;
   
   public InputHandler input;
   public Mouse mouse;
   
   public Screen currentScreen;
   public GameRunner gameRunner;
   private static boolean enableCursor = true;
   
   public UniFont defaultFont;

   public DikenEngine() {
      Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
      this.setPreferredSize(size);
      this.setMaximumSize(size);
      this.setMinimumSize(size);
      this.input = new InputHandler(this);
   }

   public void start() {
      (new Thread(this, "Game Thread")).start();
   }

   public void stop() {
      this.keepRunning = false;
   }

   public void init() {
      Art.init();
      UniFont.createFont("/fonts/default_font.json", "/fonts/default_font.png", "system_font");
      Command.initCommands();
	  
	  defaultFont = UniFont.getFont("system_font");
      
      this.screenImage = new BufferedImage(DikenEngine.WIDTH, DikenEngine.HEIGHT, 2);
      this.screenBitmap = new Bitmap(this.screenImage);
      this.mouse = this.input.updateMouseStatus(SCALE);
      this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, 2), new Point(0, 0), "invisible"));
      this.requestFocus();
   }
   
   public static String generatePath() {
	   return "game/";
   }
   
   public static boolean getEnableCursor() {
	   return enableCursor;
   }
   
   public static void setEnableCursor(boolean value) {
	   enableCursor = value;
   }

   public void run() {
      this.init();
      double nsPerFrame = 1.6666666666666666E7D;
      double unprocessedTime = 0.0D;
      double maxSkipFrames = 10.0D;
      long lastTime = System.nanoTime();
      long lastFrameTime = System.currentTimeMillis();
      
      int frames = 0; 

      while(this.keepRunning) {
         long now = System.nanoTime();
         double passedTime = (double)(now - lastTime) / nsPerFrame;
         lastTime = now;
         if (passedTime < -maxSkipFrames) {
            passedTime = -maxSkipFrames;
         }

         if (passedTime > maxSkipFrames) {
            passedTime = maxSkipFrames;
         }

         unprocessedTime += passedTime;

         while(unprocessedTime > 1.0D) {
            --unprocessedTime;
            this.mouse = this.input.updateMouseStatus(SCALE);
            this.tick();
         }

         this.render(this.screenBitmap);
         ++frames;
         if (System.currentTimeMillis() > lastFrameTime + 1000L) {
            System.out.println(frames + " fps");
            lastFrameTime += 1000L;
            frames = 0;
         }

         try {
            Thread.sleep(1L);
         } catch (InterruptedException var17) {
            var17.printStackTrace();
         }
         
         swap();
      }
   }

   public void setCurrentScreen(Screen screen) {
	  input.typed = "";
	  if(currentScreen != null) {
		  currentScreen.closeScreen();
	  }
      this.currentScreen = screen;
      if (screen != null) {
         screen.engine = this;
         screen.openScreen();
      }

   }

   private void swap() {
      BufferStrategy bs = this.getBufferStrategy();
      if (bs == null) {
         this.createBufferStrategy(2);
         return;
      } else {
         Graphics g = bs.getDrawGraphics();
         
         int screenW = this.getWidth();
         int screenH = this.getHeight();
         int w = WIDTH * SCALE;
         int h = HEIGHT * SCALE;
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, screenW, screenH);
         g.drawImage(this.screenImage, (screenW - w) / 2, (screenH - h) / 2, w, h, (ImageObserver)null);
         g.dispose();
         bs.show();
      }
   }

   private void render(Bitmap screen) {
	  screen.clear(Color.black.getRGB());
	  try {
		  if (this.currentScreen != null) {
			  this.currentScreen.render(screen);
		  } else {
		      if(gameRunner != null) {
		         gameRunner.render(screen);
		      }
		  }

		  if (this.input.onScreen && enableCursor) {
		     screen.draw(Art.i.cursors[0][0], this.mouse.x - 1, this.mouse.y - 1);
		  }
	  } catch (Exception e) {
		  e.printStackTrace();
		  setCurrentScreen(new CrashScreen(e));
	  }
      

   }
   
   public static void addGameRunner(GameRunner gameRunner) {
	   if(localEngine != null) {
		   localEngine.gameRunner = gameRunner;
	   }   
   }
   
   public static void tickDimension() {
	   if(frame.isResizable()) {
		   int newWidth = DikenEngine.getEngine().getWidth();
		   int newHeight = DikenEngine.getEngine().getHeight();
		   
		   Dimension d = new Dimension(newWidth, newHeight);
		   
		   DikenEngine.WIDTH = (int) (d.getSize().getWidth() / DikenEngine.SCALE);
		   DikenEngine.HEIGHT = (int) (d.getSize().getHeight() / DikenEngine.SCALE);
		   
		   if(DikenEngine.getEngine().screenImage == null) return;
		   
		   if(DikenEngine.getEngine().screenImage.getWidth() != newWidth || DikenEngine.getEngine().screenImage.getHeight() != newHeight) {
			   DikenEngine.getEngine().screenImage = new BufferedImage(DikenEngine.WIDTH, DikenEngine.HEIGHT, 2);
			   DikenEngine.getEngine().screenBitmap = new Bitmap(DikenEngine.getEngine().screenImage);
		   }
	   }
	   
   }

   private void tick() {
	  tickDimension();
      try {
    	  if (this.currentScreen != null) {
    	      this.currentScreen.tick();
    	  } else {
    	      if(gameRunner != null) {
    	         gameRunner.tick();
    	      }
    	  }
	  } catch (Exception e) {
		  e.printStackTrace();
		  setCurrentScreen(new CrashScreen(e));
	  }
      
   }
   
   public static DikenEngine getEngine() {
	   return localEngine;
   }
   
   public static void setArgs(String[] args) {
	   staticArgs = args;
   }
   
   public static String getArgValue(String s) {
	   if(s == null) return "";
	   if(staticArgs == null) return "";
	   String value = "";
	   for (int i = 0; i < staticArgs.length; i++) {
		   if (staticArgs[i].startsWith(s)) {
			   value = staticArgs[i];
		   }
	   }
	   if (value.isEmpty()) {
		   return "";
	   }
	   String[] a = value.split("=");
	   return a[1];
   }
   
   public static DikenEngine initEngine(int width, int height, int scale, String title) {   
	   DikenEngine.WIDTH = width;
	   DikenEngine.HEIGHT = height;
	   DikenEngine.SCALE = scale;
	   
	   if(title == null) {
		   title = "Untitled Game";
	   }
	   
	   localEngine = new DikenEngine();
	   frame = new JFrame(title);
	   frame.add(localEngine);
	   frame.pack();
	   frame.setResizable(false);
	   frame.setLocationRelativeTo((Component)null);
	   frame.setDefaultCloseOperation(3);
	   frame.setVisible(true);
	   
	   localEngine.start();
	   
	   return localEngine;
   }
   
   public void setDefaultFont(UniFont uniFont) {
	   this.defaultFont = uniFont;
   }
   
   public static void main(String[] args) {
	   initEngine(320, 240, 3, "Hello, World!");
	   getEngine().setCurrentScreen(new ConsoleScreen());
   }
   
}
