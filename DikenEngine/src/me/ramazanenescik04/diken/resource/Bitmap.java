package me.ramazanenescik04.diken.resource;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.imageio.ImageIO;

public class Bitmap implements IResource {
	private static final long serialVersionUID = 1L;
	public final int[] pixels;
	public final int w, h;
	public int xOffs;
	public int yOffs;
	public boolean xFlip = false;
	
	public static final Bitmap empty = new Bitmap(1, 1);

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
	
	public BufferedImage toImage() {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, w, h, pixels, 0, w);
		return img;
	}
	
	public Bitmap resize(int newWidth, int newHeight) {
		Bitmap resizedBitmap;
		BufferedImage bitmapImg, resizedBitmapImg;
		bitmapImg = toImage();
		resizedBitmapImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = resizedBitmapImg.createGraphics();
		g.drawImage(bitmapImg.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST), 0, 0, null);
		g.dispose();
		
		resizedBitmap = IOResource.toBitmap(resizedBitmapImg);
		
		return resizedBitmap;
	}
	
	public Bitmap resize(int scale) {
		return resize(w * scale, h * scale);
	}
	
	public Bitmap rotate(double angle) {
	    double radians = Math.toRadians(angle);
	    double cosA = Math.cos(radians);
	    double sinA = Math.sin(radians);

	    // Yeni bitmap boyutlarını hesapla
	    int newWidth = (int) Math.abs(w * cosA) + (int) Math.abs(h * sinA);
	    int newHeight = (int) Math.abs(w * sinA) + (int) Math.abs(h * cosA);
	    Bitmap rotated = new Bitmap(newWidth, newHeight);

	    // Merkez koordinatları
	    int x0 = w / 2;
	    int y0 = h / 2;
	    int newX0 = newWidth / 2;
	    int newY0 = newHeight / 2;

	    for (int y = 0; y < newHeight; y++) {
	        for (int x = 0; x < newWidth; x++) {
	            // Yeni bitmap'teki (x, y) pikseli, orijinal bitmap'teki koordinatları geri hesapla
	            int dx = x - newX0;
	            int dy = y - newY0;

	            double oldX = dx * cosA + dy * sinA + x0;
	            double oldY = -dx * sinA + dy * cosA + y0;

	            // Eğer orijinal bitmap sınırları içindeyse interpolasyonu uygula
	            if (oldX >= 0 && oldX < w - 1 && oldY >= 0 && oldY < h - 1) {
	                int xFloor = (int) Math.floor(oldX); // Sol piksel (tam sayı)
	                int yFloor = (int) Math.floor(oldY); // Üst piksel (tam sayı)
	                int xCeil = xFloor + 1;             // Sağ piksel
	                int yCeil = yFloor + 1;             // Alt piksel

	                // Piksel renklerini al (sınır kontrolü yap)
	                int topLeft = pixels[yFloor * w + xFloor];
	                int topRight = pixels[yFloor * w + (xCeil < w ? xCeil : xFloor)];
	                int bottomLeft = pixels[(yCeil < h ? yCeil : yFloor) * w + xFloor];
	                int bottomRight = pixels[(yCeil < h ? yCeil : yFloor) * w + (xCeil < w ? xCeil : xFloor)];

	                // Kesirli kısımları al (ağırlıklar)
	                double xWeight = oldX - xFloor;
	                double yWeight = oldY - yFloor;

	                // Renk bileşenlerini interpolasyonla hesapla
	                int interpolatedColor = bilinearInterpolation(topLeft, topRight, bottomLeft, bottomRight, xWeight, yWeight);

	                // Rotated bitmap'e yeni renk ata
	                rotated.pixels[y * newWidth + x] = interpolatedColor;
	            } else {
	                // Eğer orijinal bitmap'in dışına düşüyorsa siyah renk ata
	                rotated.pixels[y * newWidth + x] = 0; // Siyah (0x00000000)
	            }
	        }
	    }

	    return rotated;
	}

	// Bilinear interpolation metodu
	private int bilinearInterpolation(int topLeft, int topRight, int bottomLeft, int bottomRight, double xWeight, double yWeight) {
	    // Renk bileşenlerini parçala (ARGB formatında)
	    int aTL = (topLeft >> 24) & 0xFF, rTL = (topLeft >> 16) & 0xFF, gTL = (topLeft >> 8) & 0xFF, bTL = topLeft & 0xFF;
	    int aTR = (topRight >> 24) & 0xFF, rTR = (topRight >> 16) & 0xFF, gTR = (topRight >> 8) & 0xFF, bTR = topRight & 0xFF;
	    int aBL = (bottomLeft >> 24) & 0xFF, rBL = (bottomLeft >> 16) & 0xFF, gBL = (bottomLeft >> 8) & 0xFF, bBL = bottomLeft & 0xFF;
	    int aBR = (bottomRight >> 24) & 0xFF, rBR = (bottomRight >> 16) & 0xFF, gBR = (bottomRight >> 8) & 0xFF, bBR = bottomRight & 0xFF;

	    // Üst ve alt interpolasyonu yap
	    double aTop = aTL + xWeight * (aTR - aTL);
	    double rTop = rTL + xWeight * (rTR - rTL);
	    double gTop = gTL + xWeight * (gTR - gTL);
	    double bTop = bTL + xWeight * (bTR - bTL);

	    double aBottom = aBL + xWeight * (aBR - aBL);
	    double rBottom = rBL + xWeight * (rBR - rBL);
	    double gBottom = gBL + xWeight * (gBR - gBL);
	    double bBottom = bBL + xWeight * (bBR - bBL);

	    // Üst ve alt sonuçlarını birleştir
	    int a = (int) (aTop + yWeight * (aBottom - aTop));
	    int r = (int) (rTop + yWeight * (rBottom - rTop));
	    int g = (int) (gTop + yWeight * (gBottom - gTop));
	    int b = (int) (bTop + yWeight * (bBottom - bTop));

	    // Son rengi birleştir
	    return (a << 24) | (r << 16) | (g << 8) | b;
	}

	public void drawLine(int x1, int y1, int x2, int y2, int color, int width) {
	    x1 += this.xOffs;
	    y1 += this.yOffs;
	    x2 += this.xOffs;
	    y2 += this.yOffs;
	    int dx = Math.abs(x2 - x1);
	    int dy = Math.abs(y2 - y1);
	    
	    int sx = x1 < x2 ? 1 : -1;
	    int sy = y1 < y2 ? 1 : -1;
	    
	    int err = dx - dy;
	    
	    // Genişlik için başlangıç ve bitiş ofsetleri
	    int startX, endX, startY, endY;
	    if (width % 2 == 0) {
	        startX = -width / 2;
	        endX = width / 2 - 1;
	        startY = startX;
	        endY = endX;
	    } else {
	        startX = -(width - 1) / 2;
	        endX = (width - 1) / 2;
	        startY = startX;
	        endY = endX;
	    }
	    
	    while (true) {
	        // Mevcut nokta etrafında genişlik karesi çiz
	        for (int ox = startX; ox <= endX; ox++) {
	            for (int oy = startY; oy <= endY; oy++) {
	                int px = x1 + ox;
	                int py = y1 + oy;
	                if (px >= 0 && px < w && py >= 0 && py < h) {
	                    pixels[py * w + px] = color;
	                }
	            }
	        }
	        
	        if (x1 == x2 && y1 == y2) break;
	        
	        int e2 = 2 * err;
	        
	        if (e2 > -dy) {
	            err -= dy;
	            x1 += sx;
	        }
	        
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
	
	// Düzeltilmiş blend metodu
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
	        int dp = y * w;

	        for (int x = x0; x < x1; x++) {
	            int c = b.pixels[sp + x];
	            int a = (c >> 24) & 0xff;
	            
	            // Sadece alfa değeri > 0 olan pikselleri işle
	            if (a > 0) {
	                int bgColor = pixels[dp + x];
	                
	                // Arka plan ve kaynak pikselin renk bileşenlerini çıkar
	                int bgR = (bgColor >> 16) & 0xff;
	                int bgG = (bgColor >> 8) & 0xff;
	                int bgB = bgColor & 0xff;
	                
	                int srcR = (c >> 16) & 0xff;
	                int srcG = (c >> 8) & 0xff;
	                int srcB = c & 0xff;
	                
	                // Alfa değerine göre renk karışımını hesapla
	                int newR = (srcR * a + bgR * (255 - a)) / 255;
	                int newG = (srcG * a + bgG * (255 - a)) / 255;
	                int newB = (srcB * a + bgB * (255 - a)) / 255;
	                
	                // Yeni pikseli ayarla (alfa kanalı tamamen opak)
	                pixels[dp + x] = 0xff000000 | (newR << 16) | (newG << 8) | newB;
	            }
	        }
	    }
	}

	// Düzeltilmiş blendDraw metodu
	// Düzeltilmiş blendDraw metodu
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

	    // col renginin bileşenlerini çıkar
	    int colR = (col >> 16) & 0xff;
	    int colG = (col >> 8) & 0xff;
	    int colB = col & 0xff;

	    if (xFlip) {
	        for (int y = y0; y < y1; y++) {
	            int sp = (y - yp) * b.w + b.w - 1;
	            int dp = y * w;

	            for (int x = x0; x < x1; x++) {
	                int c = b.pixels[sp - (x - xp)];
	                int a = (c >> 24) & 0xff;
	                
	                if (a > 0) {
	                    int srcR = (c >> 16) & 0xff;
	                    int srcG = (c >> 8) & 0xff;
	                    int srcB = c & 0xff;
	                    
	                    // Kaynak ve col değerini karıştır
	                    // Burada düzeltme - renkleri doğru şekilde karıştırma
	                    int blendR = (srcR * colR) / 255;
	                    int blendG = (srcG * colG) / 255;
	                    int blendB = (srcB * colB) / 255;
	                    
	                    // Mevcut piksel ile alfa değerine göre karıştır
	                    int bgColor = pixels[dp + x];
	                    int bgR = (bgColor >> 16) & 0xff;
	                    int bgG = (bgColor >> 8) & 0xff;
	                    int bgB = bgColor & 0xff;
	                    
	                    int newR = (blendR * a + bgR * (255 - a)) / 255;
	                    int newG = (blendG * a + bgG * (255 - a)) / 255;
	                    int newB = (blendB * a + bgB * (255 - a)) / 255;
	                    
	                    pixels[dp + x] = 0xff000000 | (newR << 16) | (newG << 8) | newB;
	                }
	            }
	        }
	    } else {
	        for (int y = y0; y < y1; y++) {
	            int sp = (y - yp) * b.w;
	            int dp = y * w;

	            for (int x = x0; x < x1; x++) {
	                int c = b.pixels[sp + (x - xp)];
	                int a = (c >> 24) & 0xff;
	                
	                if (a > 0) {
	                    int srcR = (c >> 16) & 0xff;
	                    int srcG = (c >> 8) & 0xff;
	                    int srcB = c & 0xff;
	                    
	                    // Kaynak ve col değerini karıştır
	                    // Burada düzeltme - renkleri doğru şekilde karıştırma
	                    int blendR = (srcR * colR) / 255;
	                    int blendG = (srcG * colG) / 255;
	                    int blendB = (srcB * colB) / 255;
	                    
	                    // Mevcut piksel ile alfa değerine göre karıştır
	                    int bgColor = pixels[dp + x];
	                    int bgR = (bgColor >> 16) & 0xff;
	                    int bgG = (bgColor >> 8) & 0xff;
	                    int bgB = bgColor & 0xff;
	                    
	                    int newR = (blendR * a + bgR * (255 - a)) / 255;
	                    int newG = (blendG * a + bgG * (255 - a)) / 255;
	                    int newB = (blendB * a + bgB * (255 - a)) / 255;
	                    
	                    pixels[dp + x] = 0xff000000 | (newR << 16) | (newG << 8) | newB;
	                }
	            }
	        }
	    }
	}

	// Düzeltilmiş blendFill metodu
	public void blendFill(int x0, int y0, int x1, int y1, int color) {
	    x0 += xOffs;
	    y0 += yOffs;
	    x1 += xOffs;
	    y1 += yOffs;
	    if (x0 < 0) x0 = 0;
	    if (y0 < 0) y0 = 0;
	    if (x1 >= w) x1 = w - 1;
	    if (y1 >= h) y1 = h - 1;
	    
	    // color renginin bileşenlerini ve alfa değerini çıkar
	    int a = (color >> 24) & 0xff;
	    int srcR = (color >> 16) & 0xff;
	    int srcG = (color >> 8) & 0xff;
	    int srcB = color & 0xff;
	    
	    // Alfa değeri belirtilmemişse (0 ise), tamamen opak kabul et
	    if (a == 0) a = 255;
	    
	    for (int y = y0; y <= y1; y++) {
	        for (int x = x0; x <= x1; x++) {
	            int bgColor = pixels[x + y * w];
	            int bgR = (bgColor >> 16) & 0xff;
	            int bgG = (bgColor >> 8) & 0xff;
	            int bgB = bgColor & 0xff;
	            
	            int newR = (srcR * a + bgR * (255 - a)) / 255;
	            int newG = (srcG * a + bgG * (255 - a)) / 255;
	            int newB = (srcB * a + bgB * (255 - a)) / 255;
	            
	            pixels[x + y * w] = 0xff000000 | (newR << 16) | (newG << 8) | newB;
	        }
	    }
	}

	// Düzeltilmiş fogBlend metodu
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
	        int dp = y * w;

	        for (int x = x0; x < x1; x++) {
	            int c = b.pixels[sp + x];
	            // Eğer piksel şeffaf değilse işleme devam et
	            if (c != 0) {
	                // Alfa değerini al (0-255 arası)
	                int fogIntensity = c & 0xff;
	                // Sis yoğunluğu değeri olmadığında işlem yapma
	                if (fogIntensity > 0) {
	                    int bgColor = pixels[dp + x];
	                    int bgR = (bgColor >> 16) & 0xff;
	                    int bgG = (bgColor >> 8) & 0xff;
	                    int bgB = bgColor & 0xff;
	                    
	                    // Gri tonu hesapla
	                    int gray = (bgR * 30 + bgG * 59 + bgB * 11) / 100;
	                    
	                    // Sis yoğunluğuna göre karışım hesapla
	                    // fogIntensity ne kadar yüksekse o kadar sis efekti olacak
	                    int ic = 255 - fogIntensity;
	                    
	                    int newR = (bgR * ic + gray * fogIntensity) / 255;
	                    int newG = (bgG * ic + gray * fogIntensity) / 255;
	                    int newB = (bgB * ic + gray * fogIntensity) / 255;
	                    
	                    pixels[dp + x] = 0xff000000 | (newR << 16) | (newG << 8) | newB;
	                }
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
	
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints, int color) {
	    // Apply offsets
	    int[] xp = new int[nPoints];
	    int[] yp = new int[nPoints];
	    for (int i = 0; i < nPoints; i++) {
	        xp[i] = xPoints[i] + xOffs;
	        yp[i] = yPoints[i] + yOffs;
	    }
	    
	    // Find the bounding box of the polygon
	    int minX = Integer.MAX_VALUE;
	    int maxX = Integer.MIN_VALUE;
	    int minY = Integer.MAX_VALUE;
	    int maxY = Integer.MIN_VALUE;
	    
	    for (int i = 0; i < nPoints; i++) {
	        minX = Math.min(minX, xp[i]);
	        maxX = Math.max(maxX, xp[i]);
	        minY = Math.min(minY, yp[i]);
	        maxY = Math.max(maxY, yp[i]);
	    }
	    
	    // Clip to bitmap boundaries
	    minX = Math.max(0, minX);
	    minY = Math.max(0, minY);
	    maxX = Math.min(w - 1, maxX);
	    maxY = Math.min(h - 1, maxY);
	    
	    // Scan each row within bounding box
	    for (int y = minY; y <= maxY; y++) {
	        // Find intersections with polygon edges for this scanline
	        List<Integer> intersections = new ArrayList<>();
	        
	        for (int i = 0; i < nPoints; i++) {
	            int j = (i + 1) % nPoints; // Next vertex
	            
	            // Skip horizontal edges
	            if (yp[i] == yp[j]) continue;
	            
	            // Check if the edge crosses this scanline
	            if ((yp[i] <= y && y < yp[j]) || (yp[j] <= y && y < yp[i])) {
	                // Calculate x-coordinate of intersection
	                int x = xp[i] + (y - yp[i]) * (xp[j] - xp[i]) / (yp[j] - yp[i]);
	                intersections.add(x);
	            }
	        }
	        
	        // Sort intersections by x-coordinate
	        Collections.sort(intersections);
	        
	        // Fill pixels between intersection pairs
	        for (int i = 0; i < intersections.size(); i += 2) {
	            if (i + 1 < intersections.size()) {
	                int startX = Math.max(minX, intersections.get(i));
	                int endX = Math.min(maxX, intersections.get(i + 1));
	                
	                // Fill the span
	                for (int x = startX; x <= endX; x++) {
	                    pixels[y * w + x] = color;
	                }
	            }
	        }
	    }
	}

	// Convenience overload that takes exactly 3 points (for triangles)
	public void fillPolygon(int[] xPoints, int[] yPoints, int color) {
	    fillPolygon(xPoints, yPoints, xPoints.length, color);
	}
	
	public Bitmap clone() {
		return new Bitmap(w, h, Arrays.copyOf(pixels, pixels.length));
	}
	
	public Bitmap clone(int w, int h) {
		Bitmap newBitmap = clone();
		Bitmap resizedBitmap = newBitmap.resize( w, h);
		return resizedBitmap;
	}

	@Override
	public EnumResource getResourceType() {
		return EnumResource.IMAGE;
	}

	public int getPixel(int srcX, int srcY) {
		return pixels[srcX + srcY * w];
	}

	public static Bitmap createClearedBitmap(int i, int j, int k) {
		Bitmap bitmap = new Bitmap(i, j);
		bitmap.clear(k);
		return bitmap;
	}

	public void saveResource(OutputStream stream) {
		try {
			ImageIO.write(toImage(), "png", stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
