package com.emirenesgames.engine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.emirenesgames.engine.console.Command;
import com.emirenesgames.engine.console.Console;
import com.emirenesgames.engine.gui.ConsoleScreen;
import com.emirenesgames.engine.gui.CrashScreen;
import com.emirenesgames.engine.gui.DefaultLoadingScreen;
import com.emirenesgames.engine.gui.Screen;
import com.emirenesgames.engine.gui.Text;
import com.emirenesgames.engine.gui.UniFont;
import com.emirenesgames.engine.gui.window.Window;
import com.emirenesgames.engine.gui.window.WindowManager;

/**
 * DikenEngine, Oyun Motorunun Temel Ayarlarınon Değiştirildiği Sınıftır.
 **/
public class DikenEngine extends JPanel implements Runnable, WindowListener {
	private static final long serialVersionUID = 1L;
	
	/** Oyun Motorun Genişliği **/
	public static int WIDTH = 320;
	
	/** Oyun Motorun Yüksekliği **/
	public static int HEIGHT = 240;
	
	/** Çizerken Boyutlandırma Yapar **/
	public static int SCALE = 3;
	
	private int tmpW, tmpH;
	
	/** Motor Sürümü **/
	public static final String VERSION = "v0.7.0";

	/** Hedef FPS **/
	public long TARGET_FPS = 60;
	
	/** Motor Penceresi **/
	public JFrame engineWindow;

	/** Şu Anki Ekran **/
	public Screen currentScreen;

	/** Oyun Motorun Varsayılan Yazı Tipi **/
	public UniFont defaultFont;
	
	/** DikenEngine.getEngine() için **/
	private static DikenEngine instance;
	
	/** Ekran Bitmap'i **/
	private Bitmap screenBitmap;

	/** Oyun Motoru Çalışma Durumu **/
	private boolean running;

	public int fps;

	/** Klavye ve Fare Tıklamaları Algılar */
	public InputHandler input;
	
	/** Fare Kordinatları Verir */
	public Mouse mouse;

	/** Oyun Yöneticisi **/
	public GameManager gManager;

	private Thread engineThread;

	private boolean fullscreen = false;

	/** Fare Resmi **/
	public Bitmap cursorBitmap;

	/** Resim **/
	private volatile BufferedImage screenImage;
	private Init init;
	
	/** Debug **/
	private String[] debug = new String[32000];

	public WindowManager wManager;
	
	/** initEngine Kullanılmalıdır **/
	private DikenEngine() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		
		this.setMaximumSize(size);
		this.setMinimumSize(new Dimension(100, 100));
		this.setPreferredSize(size);
		
		this.input = new InputHandler(this);
	}
	
	public void addInit(Init init) {
		this.init = init;
	}

	public void run() {
	    try {
	        this.init();
	        long targetFrameTime = 1000000000L / TARGET_FPS; // Time per frame in nanoseconds
	        long fixedUpdateTime = 1000000000L / 60; // Fixed update rate at 60Hz
	        long lastRenderTime = System.nanoTime();
	        long lastUpdateTime = System.nanoTime();
	        long timer = System.currentTimeMillis();
	        int frames = 0;
	        double accumulator = 0;

	        while(running) {
	        	targetFrameTime = 1000000000L / TARGET_FPS; // Time per frame in nanoseconds
	            long currentTime = System.nanoTime();
	            
	            // Frame time for rendering
	            long frameTimeDelta = currentTime - lastRenderTime;
	            
	            // Handling variable frame rate rendering
	            if(frameTimeDelta >= targetFrameTime) {
	                lastRenderTime = currentTime;
	                
	                // Update mouse input on every frame
	                mouse = input.updateMouseStatus(SCALE);
	                
	                // Time since last game logic update
	                long updateDelta = currentTime - lastUpdateTime;
	                accumulator += updateDelta;
	                lastUpdateTime = currentTime;
	                
	                // Run game logic at a fixed rate (60 times per second)
	                while(accumulator >= fixedUpdateTime) {
	                    tick();
	                    accumulator -= fixedUpdateTime;
	                }
	                
	                // Render at the target frame rate
	                render(screenBitmap);
	                frames++;
	                
	                if(Boolean.parseBoolean(gManager.config.getProperty("sync"))) {
	                    Toolkit.getDefaultToolkit().sync();
	                }
	                
	                // FPS calculation
	                if(System.currentTimeMillis() - timer > 1000) {
	                    fps = frames;
	                    frames = 0;
	                    timer += 1000;
	                }
	                
	                // EDT üzerinde repaint çağır
	                SwingUtilities.invokeLater(() -> {
	                    try {
	                        repaint();
	                    } catch(Exception e) {
	                        e.printStackTrace();
	                    }
	                });
	            }
	            
	            // Sleep to save CPU
	            long sleepTime = (lastRenderTime - System.nanoTime() + targetFrameTime) / 1000000;
	            if(sleepTime > 0) {
	                Thread.sleep(sleepTime);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        this.crashScreen(e);
	    }
	}
	
	/** Çizme Method'u **/
	private void render(Bitmap screen) {
		try {
			if(currentScreen != null) {
				currentScreen.render(screen);
			}
			wManager.render(screen);
			if(gManager.config.getProperty("show_fps").equals("true") && !gManager.config.getProperty("debug").equals("true")) {
				Text.render("FPS: " + fps, screen, 0, 0);
			}
			if(gManager.config.getProperty("debug").equals("true") && !(this.currentScreen instanceof ConsoleScreen)) {
				for(int i = 0; i < debug.length; i++) {
					String string = debug[i];
					if(string != null) {
						screen.fill(1, 1 + (i * Text.stringBitmapAverageHeight(string, defaultFont)),
								1 + Text.stringBitmapWidth(string, defaultFont),
								1 + (i * Text.stringBitmapAverageHeight(string, defaultFont) + Text.stringBitmapAverageHeight(string, defaultFont)),
								0xff000000);
						Text.render(string, screen, 2, 2 + (i * Text.stringBitmapAverageHeight(string, defaultFont)));
					}
				}
			}
			if(gManager.cursorShowType == 1 && cursorBitmap != null) {
				screen.draw(cursorBitmap, mouse.x - 1, mouse.y - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.crashScreen(e);
		}	
	}

	/** 60 Kere Çakışır **/
	private void tick() {
		try {
			tickDimension();
			debug[1] = "FPS: " + fps;
			debug[2] = "Mouse X: " + mouse.x;
			debug[3] = "Mouse Y: " + mouse.y;
			debug[4] = "Total Vitual Window: " + wManager.size();
			debug[5] = "Java " + Runtime.version().toString();
			debug[6] = "Available processors (cores): " +  Runtime.getRuntime().availableProcessors();
			long var39 = Runtime.getRuntime().maxMemory();
            long var37 = Runtime.getRuntime().totalMemory();
            long var41 = Runtime.getRuntime().freeMemory();
            long var44 = var37 - var41;
			String var45 = "Used memory: " + var44 * 100L / var39 + "% (" + var44 / 1024L / 1024L + "MB) of " + var39 / 1024L / 1024L + "MB";
			debug[8] = var45;
            var45 = "Allocated memory: " + var37 * 100L / var39 + "% (" + var37 / 1024L / 1024L + "MB)";
			debug[9] = var45;
			
			if(this.fullscreen != Boolean.parseBoolean(gManager.config.getProperty("fullscreen")) && this.engineWindow != null) {
				this.fullscreen = Boolean.parseBoolean(gManager.config.getProperty("fullscreen"));
	   		  
	   		    if(this.fullscreen) {
	   		    	this.tmpW = WIDTH;
	   		    	this.tmpH = HEIGHT;
	   		    	engineWindow.dispose();
	   			  
	   			    engineWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
	   			    if (!engineWindow.isUndecorated()) {
	   			    	engineWindow.setUndecorated(true);
	   				}
	   				engineWindow.setVisible(true);
	   				requestFocus();
	   				engineWindow.toFront();
	   				//engineWindow.requestFocus();
	   		    } else {
	   		    	engineWindow.setSize(tmpW * SCALE, tmpH * SCALE);
	   		    	engineWindow.setLocationRelativeTo(null);
	   		    	
	   		    	engineWindow.dispose();
	   			  
	   				engineWindow.setExtendedState(JFrame.NORMAL);
	   				if (engineWindow.isUndecorated()) {
	   					engineWindow.setUndecorated(false);
	   				}
	   				engineWindow.setVisible(true);
	   				requestFocus();
	   				engineWindow.toFront();
	   				//engineWindow.requestFocus();
	   		    }
			}
			if (engineWindow != null) {
				if(engineWindow.getTitle() != gManager.config.getProperty("title")) {
					engineWindow.setTitle(gManager.config.getProperty("title"));
		   	  	}
			}
	   		if(input.keysDown[KeyEvent.VK_F11]) {
	   			input.keysDown[KeyEvent.VK_F11] = false;
	   		  	gManager.config.setProperty("fullscreen", "" + (!Boolean.parseBoolean(gManager.config.getProperty("fullscreen"))));
	   		}
	   		if(input.keysDown[KeyEvent.VK_END] && gManager.config.getProperty("console").equals("true") && !(this.currentScreen instanceof ConsoleScreen)) {
	   			input.keysDown[KeyEvent.VK_END] = false;
	   			this.setCurrentScreen(new ConsoleScreen(this.currentScreen));
	   	  	}
	   		Language lang = Language.i;
	   		if(lang.selectedLanguage != gManager.config.getProperty("lang")) {
	   			lang.setDefaultLang(gManager.config.getProperty("lang"));
	   		}
	   		wManager.tick();
	   		if(currentScreen != null) {
	   			currentScreen.width = DikenEngine.WIDTH;
	   			currentScreen.height = DikenEngine.HEIGHT;
				currentScreen.tick();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.crashScreen(e);
		}
	}

	/** Oyun Motorunun Araçlarını Ayarlamaları Yapar **/
	private void init() {
		Art.init();
		UniFont.createFont("default_font");
		UniFont.createFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		if (init != null) {
			init.fontLoadBefore();
		}
		
		this.defaultFont = UniFont.getFont("Dialog.plain.12");
		this.gManager.setLoadingScreen(new DefaultLoadingScreen("Yazı Tipiler Yükleniyor... Lütfen Bekleyin"));
		
		new Thread(new Runnable() {
			
			public void run() {
				gManager.openLoadingScreen();
				
				UniFont.createFont(new Font(Font.DIALOG, Font.BOLD, 12));
				UniFont.createFont(new Font(Font.DIALOG, Font.ITALIC, 12));
				UniFont.createFont(new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, 12));
				UniFont.createFont(new Font(Font.DIALOG, Font.BOLD, 24));
				
				UniFont.createFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
				UniFont.createFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
				UniFont.createFont(new Font(Font.MONOSPACED, Font.ITALIC, 12));
				UniFont.createFont(new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, 12));
				
				UniFont.createFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
				UniFont.createFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
				UniFont.createFont(new Font(Font.DIALOG_INPUT, Font.ITALIC, 12));
				UniFont.createFont(new Font(Font.DIALOG_INPUT, Font.BOLD | Font.ITALIC, 12));
				
				UniFont.createFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
				UniFont.createFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
				UniFont.createFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
				UniFont.createFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 12));
				
				UniFont.createFont(new Font(Font.SERIF, Font.PLAIN, 12));
				UniFont.createFont(new Font(Font.SERIF, Font.BOLD, 12));
				UniFont.createFont(new Font(Font.SERIF, Font.ITALIC, 12));
				UniFont.createFont(new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 12));
				
				if (init != null) {
					init.fontLoad();
				}
				
				System.out.println("Yazı Tipi Yükleme Tamamlandı!");
				DikenEngine.this.defaultFont = UniFont.getFont("default_font");
				
				gManager.openMainMenu();
			}
			
		}, "Custom Font Load").start();
		
		if (init != null) {
			init.fontLoadAfter();
		}

		Command.initCommands();
		
		cursorBitmap = Art.i.cursors[0][1];
		
		this.screenImage = new BufferedImage(DikenEngine.WIDTH, DikenEngine.HEIGHT, 2);
	    this.screenBitmap = new Bitmap(screenImage);
		
		this.mouse = this.input.updateMouseStatus(SCALE);		
		this.wManager = new WindowManager();
		
		
		this.setDoubleBuffered(true);
		
		if (gManager.cursorShowType != 2) {this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, 2), new Point(0, 0), "invisible"));}
			
		this.requestFocus();
		
		Console.println("DikenEngine [" + DikenEngine.VERSION + "]");
	    Console.println("Java: " + getJavaVersion());
	    
	    debug[0] = "DikenEngine [" + DikenEngine.VERSION + "]";
	      
	    if(getJavaVersion() < 9) {
	    	Console.println("Java 8 ve altı çalıştırılması önerilmez.");
	    }
	    
	    if (init != null) {
			init.systemInited();
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
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    // Geçici referans alarak thread güvenliği
	    BufferedImage currentImage = screenImage;
	    if(currentImage != null) {
	        int screenW = getWidth();
	        int screenH = getHeight();
	        
	        // Orijinal boyutlar
	        int originalW = currentImage.getWidth();
	        int originalH = currentImage.getHeight();
	        
	        // Ölçeklenmiş boyutlar
	        int scaledW = originalW * SCALE;
	        int scaledH = originalH * SCALE;
	        
	        // Merkezleme
	        int x = (screenW - scaledW) / 2;
	        int y = (screenH - scaledH) / 2;
	        
	        // Yüksek kaliteli ölçeklendirme
	        Graphics2D g2d = (Graphics2D)g;
	        if(gManager.config.get("antialiasing") == "true") {
	        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        } 
        
	        g2d.drawImage(currentImage, x, y, scaledW, scaledH, null);
	    }
	}

	/** Oyun Motorunu Ayarlarını Sağlar **/
	public static DikenEngine initEngine(int width, int height, int scale, String title) {
		DikenEngine engine = initEngineNonFrame(width, height, scale, title);
		JFrame engineWindow0 = new JFrame(title);
		
		engineWindow0.add(engine);
		engineWindow0.pack();
		try {
			engineWindow0.setIconImage(ImageIO.read(DikenEngine.class.getResourceAsStream("/icon.png")));
		} catch (IOException | IllegalArgumentException e) {
		}
		
		engineWindow0.addWindowListener(engine);
		engineWindow0.setLocationRelativeTo(null);		
		engineWindow0.setResizable(false);
		
		engine.engineWindow = engineWindow0;
		DikenEngine.instance = engine;
		
		return DikenEngine.instance;
	}
	
	public static DikenEngine initEngineNonFrame(int width, int height, int scale, String title) {
		DikenEngine.WIDTH = width;
		DikenEngine.HEIGHT = height;
		DikenEngine.SCALE = scale;
		
		if(title == null) {
			title = "Untitled Game";
		}
		
		DikenEngine engine = new DikenEngine();
		engine.screenBitmap = new Bitmap(WIDTH, HEIGHT);
		DikenEngine.instance = engine;
		
		DikenEngine.instance.gManager = new GameManager();
		DikenEngine.instance.gManager.config.setProperty("title", title);
		
		return DikenEngine.instance;
	}
	
	/** Boyutlandırılabilir Ekranlarda Büyütürken Bitmap'de Büyüyüp Küçülür **/
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
	
	/** Oyun Motorunu Başlatır **/
	public void startEngine() {
		engineThread = new Thread(this, "Engine Loop Thread");
		
		if(engineThread.isAlive()) {
			System.err.println("HATA: Zaten Motor Çalışıyor.");
			return;
		}
		if (engineWindow != null) {
			engineWindow.setVisible(true);
		}
		running = true;
		engineThread.start();
	}

	/** Oyun Motorunu Elde Edilmesini Sağlar **/
	public static DikenEngine getEngine() {
		return DikenEngine.instance;
	}

	/** Ekranı Değiştirir **/
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

	public void crashScreen(Exception e) {
		if (this.gManager.config.getProperty("legacy-crash").equals("true")) {
			this.setCurrentScreen(new CrashScreen(e));
		} else {
			StringWriter sWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(sWriter));
			JOptionPane.showMessageDialog(null, "Hata: " + sWriter.toString() + "\n\nLütfen Github'dan Bildiriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
			if (engineWindow != null) {
				engineWindow.dispose();
			}
			   
			System.exit(0);
		}
	}

	public void stop() {
		this.running = false;
		
		try {
			this.engineThread.join();
		} catch (InterruptedException e) {
			;
		}
	}
	
	/** Test Kod **/
	public static void main(String[] args) {
		DikenEngine engine = DikenEngine.initEngine(320 * 2, 240 * 2, 2, "Diken Engine " + DikenEngine.VERSION);
		engine.engineWindow.setResizable(true);
		engine.startEngine();
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		if(currentScreen != null) {
			currentScreen.closeScreen();
		}
		gManager.saveConfig();
		this.stop();
		System.exit(0);
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}
