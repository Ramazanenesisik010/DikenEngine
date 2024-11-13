package com.emirenesgames.engine;

import com.emirenesgames.engine.console.Command;
import com.emirenesgames.engine.console.Console;
import com.emirenesgames.engine.gui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DikenEngine extends Canvas implements Runnable {
   private static final long serialVersionUID = 1L;
   public static int WIDTH = 320;
   public static int HEIGHT = 240;
   public static int SCALE = 3;
   
   public static final String VERSION = "v0.5 Prerelease 2";
   
   private static DikenEngine localEngine;
   public JFrame frame = new JFrame("Diken Engine");
   private static String[] staticArgs = null;
   
   private boolean keepRunning = true;
   
   private BufferedImage screenImage;
   public Bitmap screenBitmap;
   
   public InputHandler input;
   public Mouse mouse;
   public GameManager gManager;
   
   public Screen currentScreen;
   private boolean enableCursor = true;
   private Bitmap cursorBitmap;
   
   public UniFont defaultFont;
   private int fps;
   
   private boolean fullscreen = false;

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
      UniFont.createFont("default_font");
      Command.initCommands();
	  
	  defaultFont = UniFont.getFont("default_font");
      
      this.screenImage = new BufferedImage(DikenEngine.WIDTH, DikenEngine.HEIGHT, 2);
      this.screenBitmap = new Bitmap(this.screenImage);
      this.mouse = this.input.updateMouseStatus(SCALE);
      setCursorBitmap(Art.i.cursors[0][0]);
      gManager.openMainMenu();
      this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, 2), new Point(0, 0), "invisible"));
      this.requestFocus();
      
      Console.println("DikenEngine [" + DikenEngine.VERSION + "]");
      Console.println("Java: " + getJavaVersion());
      
      if(getJavaVersion() < 9) {
    	  Console.println("Java 8 ve altı çalıştırılması önerilmez.");
      }
      
   }
   
   private static int getJavaVersion() {
	    String version = System.getProperty("java.version");
	    if(version.startsWith("1.")) {
	        version = version.substring(2, 3);
	    } else {
	        int dot = version.indexOf(".");
	        if(dot != -1) { version = version.substring(0, dot); }
	    } return Integer.parseInt(version);
   }
   
   public static String generatePath() {
	   return "game/";
   }
   
   public boolean getEnableCursor() {
	   return enableCursor;
   }
   
   public void setEnableCursor(boolean value) {
	   enableCursor = value;
   }
   
   public Bitmap getCursorBitmap() {
	   return cursorBitmap;
   }
   
   public void setCursorBitmap(Bitmap bitmap) {
	   cursorBitmap = bitmap;
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
            fps = frames;
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
	  try {
		  if (this.currentScreen != null) {
			  this.currentScreen.render(screen);
		  } else {
		  }
		  
		  if(Boolean.parseBoolean(gManager.config.getProperty("show_fps"))) {
			  Text.render("FPS: " + this.getFPS(), screen, 0, 0);
		  }

		  if (this.input.onScreen && enableCursor) {
		     screen.draw(cursorBitmap, this.mouse.x - 1, this.mouse.y - 1);
		  }
		  
		  
	  } catch (Exception e) {
		  e.printStackTrace();
		  crashScreen(e);
	  }
      

   }
   
   public void crashScreen(Exception e) {
	   stop();
	   
	   StringWriter sWriter = new StringWriter();
	   e.printStackTrace(new PrintWriter(sWriter));
	   JOptionPane.showMessageDialog(null, "Hata: " + sWriter.toString() + "\n\nLütfen Github'dan Bildiriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
	   frame.dispose();
	   
	   System.exit(0);
   }
   
   public void tickDimension() {
	   int newWidth = getWidth();
	   int newHeight = getHeight();
		   
	   Dimension d = new Dimension(newWidth / DikenEngine.SCALE, newHeight / DikenEngine.SCALE);
		   
	   DikenEngine.WIDTH = (int) (d.getSize().getWidth());
	   DikenEngine.HEIGHT = (int) (d.getSize().getHeight());
		   
	   if(screenImage == null) return;
		   
	   if(screenImage.getWidth() != d.getSize().getWidth() || screenImage.getHeight() != d.getSize().getHeight()) {
		   screenImage = new BufferedImage(DikenEngine.WIDTH, DikenEngine.HEIGHT, 2);
		   screenBitmap = new Bitmap(screenImage);
			   
		   if(currentScreen != null) {
			  setCurrentScreen(DikenEngine.getEngine().currentScreen);
		   }
	   }
	   
	   
   }

   private void tick() {
      try {
    	  tickDimension();
    	  if(this.fullscreen != Boolean.parseBoolean(gManager.config.getProperty("fullscreen"))) {
    		  this.fullscreen = Boolean.parseBoolean(gManager.config.getProperty("fullscreen"));
    		  
    		  if(this.fullscreen) {
    			  frame.dispose();
    			  
    			  frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    			  if (!frame.isUndecorated()) {
    				  frame.setUndecorated(true);
    			  }
    			  frame.setVisible(true);
    		  } else {
    			  frame.dispose();
    			  
    			  frame.setExtendedState(JFrame.NORMAL);
    			  if (frame.isUndecorated()) {
    				  frame.setUndecorated(false);
    			  }
    			  frame.setVisible(true);
    		  }
    	  }
    	  if(frame.getTitle() !=  gManager.config.getProperty("title")) {
    		  frame.setTitle( gManager.config.getProperty("title"));
    	  }
    	  if(input.keysDown[KeyEvent.VK_F11]) {
    		  input.keysDown[KeyEvent.VK_F11] = false;
    		  gManager.config.setProperty("fullscreen", "" + (!Boolean.parseBoolean(gManager.config.getProperty("fullscreen"))));
    	  }
    	  if (this.currentScreen != null) {
    	      this.currentScreen.tick();
    	  } else {
    	  }
	  } catch (Exception e) {
		  e.printStackTrace();
		  crashScreen(e);
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
	   localEngine.gManager = new GameManager();
	   localEngine.gManager.config.setProperty("title", title);
	   localEngine.frame = new JFrame(title);
	   try {
		   localEngine.frame.setIconImage(ImageIO.read(DikenEngine.class.getResourceAsStream("/icon.png")));
	   } catch (IOException | IllegalArgumentException e) {
	   }
	   localEngine.frame.add(localEngine);
	   localEngine.frame.pack();
	   localEngine.frame.setResizable(false);
	   localEngine.frame.setLocationRelativeTo((Component)null);
	   localEngine.frame.setDefaultCloseOperation(3);
	   localEngine.frame.setVisible(true);
	   
	   localEngine.start();
	   
	   return localEngine;
   }
   
   public void setDefaultFont(UniFont uniFont) {
	   this.defaultFont = uniFont;
   }
   
   public static void main(String[] args) {
	   DikenEngine engine = initEngine(320 * 2, 240 * 2, 2, "DikenEngine " + DikenEngine.VERSION);
	   engine.frame.setResizable(true);
   }
   
   public int getFPS() {   
	   return fps;
   }
}
