package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.*;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Screen {
   public ArrayList<Button> buttons = new ArrayList<Button>();
   public DikenEngine engine;
   
   private IBackground background;

   public void tick() {
	  if(background != null) {
		  background.tick();
	  }
	  
      for(int i = 0; i < this.buttons.size(); ++i) {
    	  Button btn = ((Button)this.buttons.get(i));
          if (btn.intersects(new Rectangle(this.engine.mouse.x, this.engine.mouse.y, 1, 1))) {
        	  if(this.engine.input.mb0 && btn.active) {
        		  this.engine.input.mb0 = false;
                  this.actionListener(btn.id);
                  
                  if(btn instanceof CheckBox) {
                	  ((CheckBox) btn).click();
                  }
        	  }
          }
       }
   }

   public void render(Bitmap screen) {
	   if(background != null) {
		   background.render(screen);
	   }
	   
       for(int i = 0; i < this.buttons.size(); ++i) {
           Button btn = ((Button)this.buttons.get(i));
           if(btn.intersects(new Rectangle(this.engine.mouse.x, this.engine.mouse.y, 1, 1))) {
        	   screen.blendDraw(btn.render(), btn.x, btn.y, 0xff0023a6);
           } else if(!btn.active) {
        	   screen.blendDraw(btn.render(), btn.x, btn.y, 0xff000000);
           } else {
        	   screen.draw(btn.render(), btn.x, btn.y);
           }
           
           if(!(btn instanceof CheckBox)) {
        	   Text.renderCenter(btn.text, screen, btn.x + btn.width / 2, btn.y + ((btn.height / 2) - 4));
           } else {
        	   Text.render(btn.text, screen, btn.x + (20 + 6), btn.y + ((20 / 2) - (8 / 2)));
           }
       }
   }
   
   public void keyPressed(char var1) {
   }

   protected void actionListener(int id) {
   }

   public void openScreen() {
   }
   
   public void closeScreen() {
   }
   
   public void setBackground(IBackground var1) {
	   this.background = var1;
   }
   
   public IBackground getBackground() {
	   return this.background;
   }
}
