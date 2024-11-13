package com.emirenesgames.engine;

import java.awt.image.*;
import java.util.*;

public class Bitmap {
	public final int[] pixels;
	public final int w, h;
	public int xOffs;
	public int yOffs;
	public boolean xFlip = false;

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}

	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}

	public Bitmap(BufferedImage img) {
		this.w = img.getWidth();
		this.h = img.getHeight();
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public Bitmap rotate(double angle) {
        double radians = Math.toRadians(angle);
        double cosA = Math.cos(radians);
        double sinA = Math.sin(radians);

        // Yeni bitmap'in genişlik ve yüksekliğini hesapla
        int newWidth = (int) Math.abs(w * cosA) + (int) Math.abs(h * sinA);
        int newHeight = (int) Math.abs(w * sinA) + (int) Math.abs(h * cosA);
        
        Bitmap rotated = new Bitmap(newWidth, newHeight);

        int x0 = w / 2;       // Orijinal bitmap'in merkezi (x)
        int y0 = h / 2;      // Orijinal bitmap'in merkezi (y)
        int newX0 = newWidth / 2; // Yeni bitmap'in merkezi (x)
        int newY0 = newHeight / 2; // Yeni bitmap'in merkezi (y)

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                // Mevcut piksel konumunu merkeze göre ayarla
                int dx = x - x0;
                int dy = y - y0;

                // Yeni pozisyonu hesapla
                int newX = (int) (dx * cosA - dy * sinA) + newX0;
                int newY = (int) (dx * sinA + dy * cosA) + newY0;

                // Yeni pozisyon bitmap sınırları içindeyse, pikseli ata
                if (newX >= 0 && newX < newWidth && newY >= 0 && newY < newHeight) {
                    rotated.pixels[newY * newWidth + newX] = this.pixels[y * w + x];
                }
            }
        }

        return rotated;
    }

	public void drawLine(int x1, int y1, int x2, int y2, int color) {
		x1 += this.xOffs;
		y1 += this.yOffs;
		x2 += this.xOffs;
		y2 += this.yOffs;
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		int sx = x1 < x2 ? 1 : -1;  // x yönünde adım
		int sy = y1 < y2 ? 1 : -1;  // y yönünde adım
		
		int err = dx - dy;
 
		while (true) {
			// Pikseli çiz, x1 ve y1 geçerli noktayı temsil eder
			if (x1 >= 0 && x1 < w && y1 >= 0 && y1 < h) {
				pixels[y1 * w + x1] = color;
			}
 
			// Hedef noktaya ulaşıldıysa döngüden çık
			if (x1 == x2 && y1 == y2) break;
 
			int e2 = 2 * err;
 
			// x yönünde hareket et
			if (e2 > -dy) {
				err -= dy;
				x1 += sx;
			}
 
			// y yönünde hareket et
			if (e2 < dx) {
				err += dx;
				y1 += sy;
			}
		}
	}

	public void draw(Bitmap b, int xp, int yp) {
		xp += xOffs;
		yp += yOffs;
		int x0 = xp;
		int x1 = xp + b.w;
		int y0 = yp;
		int y1 = yp + b.h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;

		if (xFlip) {
			for (int y = y0; y < y1; y++) {
				int sp = (y - yp) * b.w + xp + b.w - 1;
				int dp = (y) * w;

				for (int x = x0; x < x1; x++) {
					int c = b.pixels[sp - x];
					if (c < 0) pixels[dp + x] = b.pixels[sp - x];
				}
			}
		} else {
			for (int y = y0; y < y1; y++) {
				int sp = (y - yp) * b.w - xp;
				int dp = (y) * w;

				for (int x = x0; x < x1; x++) {
					int c = b.pixels[sp + x];
					if (c < 0) pixels[dp + x] = b.pixels[sp + x];
				}
			}
		}
	}

	public void blendDraw(Bitmap b, int xp, int yp, int col) {
		xp += xOffs;
		yp += yOffs;
		int x0 = xp;
		int x1 = xp + b.w;
		int y0 = yp;
		int y1 = yp + b.h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;

		if (xFlip) {
			for (int y = y0; y < y1; y++) {
				int sp = (y - yp) * b.w + xp + b.w - 1;
				int dp = (y) * w;

				for (int x = x0; x < x1; x++) {
					int c = b.pixels[sp - x];
					if (c < 0) pixels[dp + x] = ((b.pixels[sp - x] & 0xfefefefe) + (col & 0xfefefefe)) >> 1;
				}
			}
		} else {
			for (int y = y0; y < y1; y++) {
				int sp = (y - yp) * b.w - xp;
				int dp = (y) * w;

				for (int x = x0; x < x1; x++) {
					int c = b.pixels[sp + x];
					if (c < 0) pixels[dp + x] = ((b.pixels[sp + x] & 0xfefefefe) + (col & 0xfefefefe)) >> 1;
				}
			}
		}
	}

	public void clear(int color) {
		Arrays.fill(pixels, color);
	}

	public void setPixel(int xp, int yp, int color) {
		xp += xOffs;
		yp += yOffs;
		if (xp >= 0 && yp >= 0 && xp < w && yp < h) {
			pixels[xp + yp * w] = color;
		}

	}

	public void shade(Bitmap shadows) {
		for (int i = 0; i < pixels.length; i++) {
			if (shadows.pixels[i] > 0) {
				int r = ((pixels[i] & 0xff0000) * 200) >> 8 & 0xff0000;
				int g = ((pixels[i] & 0xff00) * 200) >> 8 & 0xff00;
				int b = ((pixels[i] & 0xff) * 200) >> 8 & 0xff;
				pixels[i] = 0xff000000 | r | g | b;
			}
		}
	}

	public void fill(int x0, int y0, int x1, int y1, int color) {
		x0 += xOffs;
		y0 += yOffs;
		x1 += xOffs;
		y1 += yOffs;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 >= w) x1 = w - 1;
		if (y1 >= h) y1 = h - 1;
		for (int y = y0; y <= y1; y++) {
			for (int x = x0; x <= x1; x++) {
				pixels[x + y * w] = color;
			}
		}
	}
	
	public void blendFill(int x0, int y0, int x1, int y1, int color) {
		x0 += xOffs;
		y0 += yOffs;
		x1 += xOffs;
		y1 += yOffs;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 >= w) x1 = w - 1;
		if (y1 >= h) y1 = h - 1;
		for (int y = y0; y <= y1; y++) {
			for (int x = x0; x <= x1; x++) {
				int col = pixels[x + y * w];
				pixels[x + y * w] = (col & 0xfefefefe) + (color & 0xfefefefe) >> 1;
			}
		}
	}

	public void box(int x0, int y0, int x1, int y1, int color) {
		x0 += xOffs;
		y0 += yOffs;
		x1 += xOffs;
		y1 += yOffs;
		int xx0 = x0;
		int yy0 = y0;
		int xx1 = x1;
		int yy1 = y1;

		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 >= w) x1 = w - 1;
		if (y1 >= h) y1 = h - 1;

		for (int y = y0; y <= y1; y++) {
			for (int x = x0; x <= x1; x++) {
				if (x == xx0 || y == yy0 || x == xx1 || y == yy1) pixels[x + y * w] = color;
				if (y > yy0 && y < yy1 && x < xx1 - 1) {
					x = xx1 - 1;
				}
			}
		}
	}

	public void blend(Bitmap b, int xp, int yp) {
		xp += xOffs;
		yp += yOffs;
		int x0 = xp;
		int x1 = xp + b.w;
		int y0 = yp;
		int y1 = yp + b.h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;

		for (int y = y0; y < y1; y++) {
			int sp = (y - yp) * b.w - xp;
			int dp = (y) * w;

			for (int x = x0; x < x1; x++) {
				int c = b.pixels[sp + x];
				int a = (c >> 24) & 0xff;
				if (a != 0) {
					int ia = 255 - a;

					int rr = (pixels[dp + x] >> 16) & 0xff;
					int gg = (pixels[dp + x] >> 8) & 0xff;
					int bb = (pixels[dp + x]) & 0xff;

					int ir = ((x ^ y) & 1) * 10 + 10;
					int ig = ir;
					int ib = ir;

					rr = (rr * ia + ir * a) / 255;
					gg = (gg * ia + ig * a) / 255;
					bb = (bb * ia + ib * a) / 255;

					pixels[dp + x] = 0xff000000 | rr << 16 | gg << 8 | bb;
				}
			}
		}
	}

	public void fogBlend(Bitmap b, int xp, int yp) {
		xp += xOffs;
		yp += yOffs;
		int x0 = xp;
		int x1 = xp + b.w;
		int y0 = yp;
		int y1 = yp + b.h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;

		for (int y = y0; y < y1; y++) {
			int sp = (y - yp) * b.w - xp;
			int dp = (y) * w;

			for (int x = x0; x < x1; x++) {
				int c = b.pixels[sp + x];
				if (c != 0) {
					c = c & 0xff;
					int ic = 255 - c;
					int rr = (pixels[dp + x] >> 16) & 0xff;
					int gg = (pixels[dp + x] >> 8) & 0xff;
					int bb = (pixels[dp + x]) & 0xff;
					int gray = (rr * 30 + gg * 59 + bb * 11) / 255;

					rr = (rr * c + gray * ic) / 255;
					gg = (gg * c + gray * ic) / 255;
					bb = (bb * c + gray * ic) / 255;

					pixels[dp + x] = 0xff000000 | rr << 16 | gg << 8 | bb;
				}
			}
		}
	}
}
