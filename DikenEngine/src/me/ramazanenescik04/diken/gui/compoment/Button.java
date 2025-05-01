package me.ramazanenescik04.diken.gui.compoment;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.InputHandler;
import me.ramazanenescik04.diken.gui.Hitbox;
import me.ramazanenescik04.diken.resource.ArrayBitmap;
import me.ramazanenescik04.diken.resource.Bitmap;
import me.ramazanenescik04.diken.resource.ResourceLocator;

public class Button extends GuiCompoment {
	private static final long serialVersionUID = 1L;
	public String text = "";
	public int renderX, renderY, xa, ya, tColor;
	
	private int textOffset = 0; // Yazı kaydırma için offset
    private boolean movingRight = true; // Yazının hareket yönü
    private final double SCROLL_SPEED = 0.49888d; // Kaydırma hızı
    private double textOffsetLong = 0;
    
    private Runnable runnable;
    
    private volatile boolean isTouching = false;
	
	public Button(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.xa = x;
		this.ya = y;
		this.renderX = xa;
		this.renderY = ya;
		this.text = text;	
		this.tColor = 0xffffffff;
	}
	
	public Button(String text, int x, int y, int width, int height, int color) {
		super(x, y, width, height);
		this.xa = x;
		this.ya = y;
		this.renderX = xa;
		this.renderY = ya;
		this.text = text;
		this.tColor = color;
	}
	
	public Bitmap render() {
		Bitmap bitmap = new Bitmap(width + 4, height + 4);
		bitmap.fill(0, 0, 0 + width, 0 + height, 0xff484848);
		
		ArrayBitmap button = (ArrayBitmap) ResourceLocator.getResource("button-array");
		
		for (int i = 0; i < width; i++) {
			bitmap.draw(button.bitmap[0][1], i, height - 12);
			bitmap.draw(button.bitmap[2][1], i, 0);
		}
		
		for (int i = 0; i < height; i++) {
			bitmap.draw(button.bitmap[1][0], 0, 0 + i);
			bitmap.draw(button.bitmap[1][1], 0 + width - 12, 0 + i);
		}
		
		bitmap.draw(button.bitmap[0][0], 0, 0 + (height - 12));
		bitmap.draw(button.bitmap[0][2], 0 + width, 0 + (height - 12));
		bitmap.draw(button.bitmap[2][0], 0, 0);
		bitmap.draw(button.bitmap[1][2], 0 + width, 0);
		
		// Yazı genişliğini kontrol et
        int textWidth = Text.stringBitmapWidth(text, DikenEngine.getEngine().defaultFont);
        
        if (textWidth > width) {     
            Text.render(text, bitmap, -textOffset + 10, height / 2 - 4, tColor);
        } else {
            // Normal merkezi render
            Text.renderCenter(text, bitmap, width / 2, height / 2 - 4, tColor);
        }
        
        if (isTouching) {
        	bitmap.box(0, 0, width + 3, height + 3, 0xffffffff);
        }
        
        if (!active) {
			bitmap.box(0, 0, width + 3, height + 3, 0x7f000000);
		}
        
        return bitmap;
	}

	public void tick(DikenEngine engine) {
		int textWidth = Text.stringBitmapWidth(text, engine.defaultFont);
		if (textWidth > width) {
			// Yazı genişlikten büyükse kaydırma işlemi yap
            if (movingRight) {
            	textOffsetLong += SCROLL_SPEED;
                if (textOffset > textWidth - width + 20) { // Biraz boşluk bırak
                    movingRight = false;
                }
            } else {
            	textOffsetLong -= SCROLL_SPEED;
                if (textOffset < 0) {
                    movingRight = true;
                }
            }
            textOffset = (int) textOffsetLong;
		}
	}

	public void mouseClicked(int x, int y, int button, boolean isTouch) {
		if (isTouch) {
			if (button == 0 && runnable != null) {
				runnable.run();
			}
		}
	}
	
	public void mouseGetInfo(int x, int y, boolean isTouch) {
		this.isTouching = isTouch;
	}

	public Button setRunnable(Runnable runnable) {
		this.runnable = runnable;
		return this;
	}
}
