package com.emirenesgames.engine;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.emirenesgames.engine.gui.Text;
import com.emirenesgames.engine.gui.UniFont;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {
   private int xm;
   private int ym;
   public boolean mb0, mb0Clicked, mb0Released;
   public boolean mb1, mb1Clicked, mb1Released;
   public boolean mb2, mb2Clicked, mb2Released;
   
   private boolean oldMb0, oldMb1, oldMb2;
   public boolean onScreen;
   private Mouse input = new Mouse();
   private Canvas canvas;
   public boolean[] keysDown = new boolean[65536];
   public String typed = "";

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
	  DikenEngine.getEngine().currentScreen.keyPressed(ke.getKeyChar());
	  if(DikenEngine.getEngine().defaultFont.charTypes.indexOf(ke.getKeyChar()) >= 0) {
		  this.typed = this.typed + ke.getKeyChar();
	  }
      
   }

   public synchronized Mouse updateMouseStatus(int scale) {
      this.input.update(this.xm / scale, this.ym / scale);
      
      this.mb0Clicked = !this.oldMb0 && mb0;
      this.mb1Clicked = !this.oldMb1 && mb1;
      this.mb2Clicked = !this.oldMb2 && mb2;
      
      this.mb0Released = this.oldMb0 && !mb0;
      this.mb1Released = this.oldMb1 && !mb1;
      this.mb2Released = this.oldMb2 && !mb2;
      
      this.oldMb0 = mb0;
      this.oldMb1 = mb1;
      this.oldMb2 = mb2;
      
      
      return this.input;
   }
}
