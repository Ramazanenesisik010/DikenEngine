package com.emirenesgames.engine.gui.window;

import java.awt.Color;
import java.awt.Point;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;

import com.emirenesgames.engine.Art;
import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.gui.Button;
import com.emirenesgames.engine.gui.CheckBox;
import com.emirenesgames.engine.gui.GuiObject;
import com.emirenesgames.engine.gui.Hitbox;
import com.emirenesgames.engine.gui.Text;
import com.emirenesgames.engine.gui.TextField;

public abstract class Window extends GuiObject implements Closeable {
	private static final long serialVersionUID = 1L;
	public int tmpWidth, tmpHeight;	
	protected DikenEngine engine;
	
	public String title;
	public boolean closed = false;
	public boolean resizable = true;
	public boolean fullscreen = false;
	private Button closeButton = new Button("X", 0, 0, 12, 12, 0);
	private Button fullscreenButton = new Button("[]", 0, 0, 12, 12, 0);
	private Button smaleButton = new Button("_", 0, 0, 12, 12, 0);
	protected List<GuiObject> buttons = new ArrayList<>();
	
	private Bitmap icon; // 16x16 pixel image
	public int tmpX, tmpY;
	
    final static int BASLIK_YUKSEKLIGI = 20;

	public Window(String string, int width, int height) {
		this(string, 180, 2, width, height);
	}
	
	public Window(String string, int x, int y, int width, int height) {
		this(string , x, y, width, height, Art.i.default_win_icons[0][0]);
	}
	
	public Window(String string, int x, int y, int width, int height, Bitmap icon) {
		super(x, y, width, height + BASLIK_YUKSEKLIGI);
		this.tmpWidth = width;
		this.tmpHeight = height + BASLIK_YUKSEKLIGI;
		this.title = string;
		this.icon = icon;
	}

	public Bitmap render() {
		int px = 0;
        int py = 0;
        int pg = width;
        int py2 = height;
		
		Bitmap bitmap = super.render();
		
		bitmap.fill(px, py, pg, py2, 0xffffffff);
		
		bitmap.fill(px, py, pg, BASLIK_YUKSEKLIGI, active ? 0xff000080: Color.GRAY.getRGB());
		
		bitmap.box(px, py, pg - 1, py2 - 1, 0xff000000);
		bitmap.drawLine(px, py + BASLIK_YUKSEKLIGI, px + pg, py + BASLIK_YUKSEKLIGI, 0xff000000, 1);
		
		int titleX, titleY;
		
		if (icon != null) {
			bitmap.draw(icon, px + 2, py + 2);
			titleX = px + 20;
			titleY = py + 13 / 2;
		} else {
			titleX = px + 2;
			titleY = py + 13 / 2;
		}
		
		//Text.render(title, bitmap, px + 10 / 2, py + 13 / 2);
		Text.render(title, bitmap, titleX, titleY);
		
		bitmap.blendDraw(closeButton.render(), closeButton.x, closeButton.y, 0xffff0000);	
		Text.renderCenter(((Button)closeButton).text, bitmap, closeButton.x + closeButton.width / 2 + 2, closeButton.y + ((closeButton.height / 2) - 2));
		
		/*bitmap.draw(fullscreenButton.render(), fullscreenButton.x, fullscreenButton.y);	
		Text.renderCenter(((Button)fullscreenButton).text, bitmap, fullscreenButton.x + fullscreenButton.width / 2 + 4, fullscreenButton.y + ((fullscreenButton.height / 2) - 2));
		
		bitmap.draw(smaleButton.render(), smaleButton.x, smaleButton.y);	
		Text.renderCenter(((Button)smaleButton).text, bitmap, smaleButton.x + smaleButton.width / 2 + 2, smaleButton.y + ((smaleButton.height / 2) - 2));*/
		
        /*bitmap.fill(px + pg - 20, py + 3, pg - 20 + 15, 18, 0xffff0000);
        bitmap.drawLine(px + pg - 17, py + 5, px + pg - 8, py + 14, 0xffffffff, 1);
        bitmap.drawLine(px + pg - 17, py + 14, px + pg - 8, py + 5, 0xffffffff, 1);*/
        
		Bitmap screenBitmap = new Bitmap(width - 2, height - BASLIK_YUKSEKLIGI - 2);
	        
	    renderPanel(screenBitmap);
	     
	    bitmap.draw(screenBitmap, px + 1, py + BASLIK_YUKSEKLIGI + 1);
		   
		
		if(active && resizable) {
			int[] resizeCornerX = {px + pg - 10, px + pg, px + pg};
			int[] resizeCornerY = {py + py2, py + py2 - 10, py + py2};
			bitmap.fillPolygon(resizeCornerX, resizeCornerY, 3, 0xff000000);
		}
		
		return bitmap;
	}
	
	protected void renderPanel(Bitmap screenBitmap) {
		for(int i = 0; i < this.buttons.size(); ++i) {
	    	GuiObject btn = this.buttons.get(i);   
	    	   if (btn instanceof Button) {
	    		   if(guiObjetIsPressed(engine.mouse.getPoint(), btn) && active) {
	    			   screenBitmap.blendDraw(btn.render(), btn.x, btn.y, 0xff0023a6);
	               } else if(!btn.active) {
	            	   screenBitmap.blendDraw(btn.render(), btn.x, btn.y, 0xff000000);
	               } else {
	            	   screenBitmap.draw(btn.render(), btn.x, btn.y);
	               }
	    	   } else {
	    		   screenBitmap.draw(btn.render(), btn.x, btn.y);
	           }
	           
	           if(btn instanceof Button) {
	        	   Text.renderCenter(((Button)btn).text, screenBitmap, btn.x + btn.width / 2, btn.y + ((btn.height / 2) - 4), ((Button)btn).tColor);
	           } else if (btn instanceof CheckBox){
	        	   Text.render(((CheckBox)btn).text, screenBitmap, btn.x + (btn.width + 6), btn.y + ((btn.height / 2) - (8 / 2)));
	           }
	           
	           if (engine.gManager.config.getProperty("debug").equals("true")) {
	        	   screenBitmap.box(btn.x, btn.y, btn.x + btn.width, btn.y + btn.height, 0xff00ff00);
			   }
	       }
	}

	public void tick() {
		int px = 0;
        int py = 0;
        int pg = width;
        int py2 = height;
		
		closeButton.setBounds(px + pg - 20, py + 3, 12, 12);
		
		fullscreenButton.setBounds(px + pg - 20 - 17, py + 3, 12, 12);
		
		smaleButton.setBounds(px + pg - 20 - 17 * 2, py + 3, 12, 12);
		
		for(int i = 0; i < buttons.size(); i++) {
			GuiObject guiObj = buttons.get(i);
			   
			guiObj.tick();
			
			if (active) {
				if(guiObjetIsPressed(engine.mouse.getPoint(), guiObj) && this.engine.input.mb0) {
					this.engine.input.mb0 = false; 
					if(guiObj instanceof Button) {
			           	int id = ((Button)guiObj).id;
			        	this.actionListener(id);
			        	this.actionListener(((Button)guiObj));
			    	} else if(guiObj instanceof CheckBox) {
			            ((CheckBox) guiObj).click();
			    	} else if(guiObj instanceof TextField) {
			            ((TextField)guiObj).setFocused(!((TextField)guiObj).isFocused());
			        }
				}
			}	   
		}
	}

	protected void actionListener(Button button) {
	}

	protected void actionListener(int id) {
	}
	
	public void keyPressed(char var1) {
		 for(int i = 0; i < this.buttons.size(); ++i) {
	    	   GuiObject btn = ((GuiObject)this.buttons.get(i));
	    	   if (btn instanceof TextField) {
	    		   ((TextField)btn).keyPressed(var1);
	    	   }
	      }
	}

	public boolean iceriyorMu(Point p) {
            return p.x >= x && p.x <= x + width &&
                   p.y >= y && p.y <= y + height;
    }
    
    public boolean baslikCubugundaMi(Point p) {
            return p.x >= x && p.x <= x + width &&
                   p.y >= y && p.y <= y + BASLIK_YUKSEKLIGI;
    }
    
    public boolean kapatmaDugmesineBasildiMi(Point p) {
        return guiObjetIsPressedNOBAR(p, closeButton);
    }
    
    public boolean fullscreenButtonPressed(Point p) {
        return guiObjetIsPressedNOBAR(p, fullscreenButton);
    }
    
    public boolean smaleButtonPressed(Point p) {
        return guiObjetIsPressedNOBAR(p, smaleButton);
    }
    
    public boolean guiObjetIsPressed(Point p, GuiObject guiObj) {
        int kapatX = guiObj.x + x, kapatY = guiObj.y + y + BASLIK_YUKSEKLIGI;
        
        return p.x >= kapatX && p.x <= kapatX + guiObj.width &&
               p.y >= kapatY && p.y <= kapatY + guiObj.height;
    }
    
    private boolean guiObjetIsPressedNOBAR(Point p, GuiObject guiObj) {
        int kapatX = guiObj.x + x, kapatY = guiObj.y + y;
        
        return p.x >= kapatX && p.x <= kapatX + guiObj.width &&
               p.y >= kapatY && p.y <= kapatY + guiObj.height;
    }

	public void close() throws IOException {
		this.closed = true;
	}
	
	public abstract void open();
	
	public static Window createEmptyWindow(String title, int x, int y, int width, int height) {
		return new Window(title, x, y, width, height) {
			private static final long serialVersionUID = 1L;
			public void open() {
				;
			}
		};
	}

}
