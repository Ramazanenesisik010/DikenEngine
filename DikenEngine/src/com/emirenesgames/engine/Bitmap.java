package com.emirenesgames.engine;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Bitmap {
   public final int[] pixels;
   public final int w;
   public final int h;
   public int xOffs;
   public int yOffs;
   public boolean xFlip = false;

   public Bitmap(int w, int h) {
      this.w = w;
      this.h = h;
      this.pixels = new int[w * h];
   }

   public Bitmap(int w, int h, int[] pixels) {
      this.w = w;
      this.h = h;
      this.pixels = pixels;
   }

   public Bitmap(BufferedImage img) {
      this.w = img.getWidth();
      this.h = img.getHeight();
      this.pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
   }

   public void draw(Bitmap b, int xp, int yp) {
      xp += this.xOffs;
      yp += this.yOffs;
      int x0 = xp;
      int x1 = xp + b.w;
      int y0 = yp;
      int y1 = yp + b.h;
      if (xp < 0) {
         x0 = 0;
      }

      if (yp < 0) {
         y0 = 0;
      }

      if (x1 > this.w) {
         x1 = this.w;
      }

      if (y1 > this.h) {
         y1 = this.h;
      }

      int y;
      int sp;
      int dp;
      int x;
      int c;
      if (this.xFlip) {
         for(y = y0; y < y1; ++y) {
            sp = (y - yp) * b.w + xp + b.w - 1;
            dp = y * this.w;

            for(x = x0; x < x1; ++x) {
               c = b.pixels[sp - x];
               if (c < 0) {
                  this.pixels[dp + x] = b.pixels[sp - x];
               }
            }
         }
      } else {
         for(y = y0; y < y1; ++y) {
            sp = (y - yp) * b.w - xp;
            dp = y * this.w;

            for(x = x0; x < x1; ++x) {
               c = b.pixels[sp + x];
               if (c < 0) {
                  this.pixels[dp + x] = b.pixels[sp + x];
               }
            }
         }
      }

   }

   public void blendDraw(Bitmap b, int xp, int yp, int col) {
      xp += this.xOffs;
      yp += this.yOffs;
      int x0 = xp;
      int x1 = xp + b.w;
      int y0 = yp;
      int y1 = yp + b.h;
      if (xp < 0) {
         x0 = 0;
      }

      if (yp < 0) {
         y0 = 0;
      }

      if (x1 > this.w) {
         x1 = this.w;
      }

      if (y1 > this.h) {
         y1 = this.h;
      }

      int y;
      int sp;
      int dp;
      int x;
      int c;
      if (this.xFlip) {
         for(y = y0; y < y1; ++y) {
            sp = (y - yp) * b.w + xp + b.w - 1;
            dp = y * this.w;

            for(x = x0; x < x1; ++x) {
               c = b.pixels[sp - x];
               if (c < 0) {
                  this.pixels[dp + x] = (b.pixels[sp - x] & -16843010) + (col & -16843010) >> 1;
               }
            }
         }
      } else {
         for(y = y0; y < y1; ++y) {
            sp = (y - yp) * b.w - xp;
            dp = y * this.w;

            for(x = x0; x < x1; ++x) {
               c = b.pixels[sp + x];
               if (c < 0) {
                  this.pixels[dp + x] = (b.pixels[sp + x] & -16843010) + (col & -16843010) >> 1;
               }
            }
         }
      }

   }

   public void clear(int color) {
      Arrays.fill(this.pixels, color);
   }

   public void setPixel(int xp, int yp, int color) {
      xp += this.xOffs;
      yp += this.yOffs;
      if (xp >= 0 && yp >= 0 && xp < this.w && yp < this.h) {
         this.pixels[xp + yp * this.w] = color;
      }

   }

   public void shade(Bitmap shadows) {
      for(int i = 0; i < this.pixels.length; ++i) {
    	 if(i == shadows.pixels.length) return;
         if (shadows.pixels[i] > 0) {
            int r = (this.pixels[i] & 16711680) * 200 >> 8 & 16711680;
            int g = (this.pixels[i] & '\uff00') * 200 >> 8 & '\uff00';
            int b = (this.pixels[i] & 255) * 200 >> 8 & 255;
            this.pixels[i] = -16777216 | r | g | b;
         }
      }

   }

   public void fill(int x0, int y0, int x1, int y1, int color) {
      x0 += this.xOffs;
      y0 += this.yOffs;
      x1 += this.xOffs;
      y1 += this.yOffs;
      if (x0 < 0) {
         x0 = 0;
      }

      if (y0 < 0) {
         y0 = 0;
      }

      if (x1 >= this.w) {
         x1 = this.w - 1;
      }

      if (y1 >= this.h) {
         y1 = this.h - 1;
      }

      for(int y = y0; y <= y1; ++y) {
         for(int x = x0; x <= x1; ++x) {
            this.pixels[x + y * this.w] = color;
         }
      }

   }

   public void box(int x0, int y0, int x1, int y1, int color) {
      x0 += this.xOffs;
      y0 += this.yOffs;
      x1 += this.xOffs;
      y1 += this.yOffs;
      int xx0 = x0;
      int yy0 = y0;
      int xx1 = x1;
      int yy1 = y1;
      if (x0 < 0) {
         x0 = 0;
      }

      if (y0 < 0) {
         y0 = 0;
      }

      if (x1 >= this.w) {
         x1 = this.w - 1;
      }

      if (y1 >= this.h) {
         y1 = this.h - 1;
      }

      for(int y = y0; y <= y1; ++y) {
         for(int x = x0; x <= x1; ++x) {
            if (x == xx0 || y == yy0 || x == xx1 || y == yy1) {
               this.pixels[x + y * this.w] = color;
            }

            if (y > yy0 && y < yy1 && x < xx1 - 1) {
               x = xx1 - 1;
            }
         }
      }

   }

   public void blend(Bitmap b, int xp, int yp) {
      xp += this.xOffs;
      yp += this.yOffs;
      int x0 = xp;
      int x1 = xp + b.w;
      int y0 = yp;
      int y1 = yp + b.h;
      if (xp < 0) {
         x0 = 0;
      }

      if (yp < 0) {
         y0 = 0;
      }

      if (x1 > this.w) {
         x1 = this.w;
      }

      if (y1 > this.h) {
         y1 = this.h;
      }

      for(int y = y0; y < y1; ++y) {
         int sp = (y - yp) * b.w - xp;
         int dp = y * this.w;

         for(int x = x0; x < x1; ++x) {
            int c = b.pixels[sp + x];
            int a = c >> 24 & 255;
            if (a != 0) {
               int ia = 255 - a;
               int rr = this.pixels[dp + x] >> 16 & 255;
               int gg = this.pixels[dp + x] >> 8 & 255;
               int bb = this.pixels[dp + x] & 255;
               int ir = ((x ^ y) & 1) * 10 + 10;
               rr = (rr * ia + ir * a) / 255;
               gg = (gg * ia + ir * a) / 255;
               bb = (bb * ia + ir * a) / 255;
               this.pixels[dp + x] = -16777216 | rr << 16 | gg << 8 | bb;
            }
         }
      }

   }

   public void fogBlend(Bitmap b, int xp, int yp) {
      xp += this.xOffs;
      yp += this.yOffs;
      int x0 = xp;
      int x1 = xp + b.w;
      int y0 = yp;
      int y1 = yp + b.h;
      if (xp < 0) {
         x0 = 0;
      }

      if (yp < 0) {
         y0 = 0;
      }

      if (x1 > this.w) {
         x1 = this.w;
      }

      if (y1 > this.h) {
         y1 = this.h;
      }

      for(int y = y0; y < y1; ++y) {
         int sp = (y - yp) * b.w - xp;
         int dp = y * this.w;

         for(int x = x0; x < x1; ++x) {
            int c = b.pixels[sp + x];
            if (c != 0) {
               c &= 255;
               int ic = 255 - c;
               int rr = this.pixels[dp + x] >> 16 & 255;
               int gg = this.pixels[dp + x] >> 8 & 255;
               int bb = this.pixels[dp + x] & 255;
               int gray = (rr * 30 + gg * 59 + bb * 11) / 255;
               rr = (rr * c + gray * ic) / 255;
               gg = (gg * c + gray * ic) / 255;
               bb = (bb * c + gray * ic) / 255;
               this.pixels[dp + x] = -16777216 | rr << 16 | gg << 8 | bb;
            }
         }
      }

   }
}
