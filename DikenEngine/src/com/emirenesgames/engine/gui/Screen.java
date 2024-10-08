package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.*;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Screen {
   public ArrayList<Button> buttons = new ArrayList<Button>();
   public DikenEngine engine;

   public void tick() {
      for(int i = 0; i < this.buttons.size(); ++i) {
    	  Button btn = ((Button)this.buttons.get(i));
    	  btn.tick();
          if (btn.intersects(new Rectangle(this.engine.mouse.x, this.engine.mouse.y, 1, 1)) && this.engine.input.mb0) {
        	  this.engine.input.mb0 = false;
             this.actionListener(btn.id);
          }
       }
   }

   public void render(Bitmap screen) {
       for(int i = 0; i < this.buttons.size(); ++i) {
           Button btn = ((Button)this.buttons.get(i));
           btn.render(screen);
       }
   }
   
   public void keyPressed(char var1) {
   }

   public void actionListener(int id) {
   }

   public void openScreen() {
   }
   
   public void closeScreen() {
   }
}
