package com.emirenesgames.engine;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {
   private int xm;
   private int ym;
   public boolean mb0;
   public boolean mb1;
   public boolean mb2;
   public boolean onScreen;
   private Mouse input = new Mouse();
   private Canvas canvas;
   public boolean[] keysDown = new boolean[65536];
   public String typed;

   public InputHandler(Canvas canvas) {
      this.canvas = canvas;
      canvas.addMouseListener(this);
      canvas.addMouseMotionListener(this);
      canvas.addKeyListener(this);
   }

   public synchronized void mouseDragged(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = true;
   }

   public synchronized void mouseMoved(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = true;
   }

   public synchronized void mouseClicked(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = true;
   }

   public synchronized void mouseEntered(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = true;
   }

   public synchronized void mouseExited(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = false;
   }

   public synchronized void mousePressed(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = true;
      if (me.getButton() == 1) {
         this.mb0 = true;
      }

      if (me.getButton() == 3) {
         this.mb1 = true;
      }

      if (me.getButton() == 2) {
         this.mb2 = true;
      }

   }

   public synchronized void mouseReleased(MouseEvent me) {
      this.xm = me.getX();
      this.ym = me.getY();
      this.onScreen = this.xm >= 0 && this.ym >= 0 && this.xm < this.canvas.getWidth() && this.ym < this.canvas.getHeight();
      if (me.getButton() == 1) {
         this.mb0 = false;
      }

      if (me.getButton() == 3) {
         this.mb1 = false;
      }

      if (me.getButton() == 2) {
         this.mb2 = false;
      }

   }

   public synchronized void keyPressed(KeyEvent ke) {
      if (ke.getKeyCode() > 0 && ke.getKeyCode() < this.keysDown.length) {
         this.keysDown[ke.getKeyCode()] = true;
      }

   }

   public synchronized void keyReleased(KeyEvent ke) {
      if (ke.getKeyCode() > 0 && ke.getKeyCode() < this.keysDown.length) {
         this.keysDown[ke.getKeyCode()] = false;
      }

   }

   public synchronized void keyTyped(KeyEvent ke) {
      this.typed = this.typed + ke.getKeyChar();
   }

   public synchronized Mouse updateMouseStatus(int scale) {
      this.input.update(this.xm / scale, this.ym / scale);
      return this.input;
   }
}
