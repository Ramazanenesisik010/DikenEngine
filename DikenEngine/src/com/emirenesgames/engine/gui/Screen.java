package com.emirenesgames.engine.gui;

import com.emirenesgames.engine.*;

import java.util.*;

public abstract class Screen {
   public List<GuiObject> buttons = new ArrayList<GuiObject>();
   public DikenEngine engine;
   
   private IBackground background;

   public void tick() {
	  if(background != null) {
		  background.tick();
	  }
	  
	  for(int i = 0; i < buttons.size(); i++) {
		   GuiObject guiObj = buttons.get(i);
		   
		   guiObj.tick();
			
		   if(guiObj.intersects(new Hitbox(this.engine.mouse.x - 1, this.engine.mouse.y - 1)) && this.engine.input.mb0) {
			   this.engine.input.mb0 = false; 
			   if(guiObj instanceof Button) {
	               int id = ((Button)guiObj).id;
	               this.actionListener(id);
	           } else if(guiObj instanceof CheckBox) {
	               ((CheckBox) guiObj).click();
	           } else if(guiObj instanceof TextField) {
	               ((TextField)guiObj).setFocused(!((TextField)guiObj).isFocused());
	           }
		   }
	   }
   }

   public void render(Bitmap screen) {
	   if(background != null) {
		   background.render(screen);
	   }
	   
       for(int i = 0; i < this.buttons.size(); ++i) {
    	   GuiObject btn = this.buttons.get(i);
    	   if (btn instanceof Button) {
    		   if(btn.intersects(new Hitbox(this.engine.mouse.x - 1, this.engine.mouse.y - 1))) {
            	   screen.blendDraw(btn.render(), btn.x, btn.y, 0xff0023a6);
               } else if(!btn.active) {
            	   screen.blendDraw(btn.render(), btn.x, btn.y, 0xff000000);
               } else {
            	   screen.draw(btn.render(), btn.x, btn.y);
               }
    	   } else {
        	   screen.draw(btn.render(), btn.x, btn.y);
           }
           
           if(btn instanceof Button) {
        	   Text.renderCenter(((Button)btn).text, screen, btn.x + btn.width / 2, btn.y + ((btn.height / 2) - 4));
           } else if (btn instanceof CheckBox){
        	   Text.render(((CheckBox)btn).text, screen, btn.x + (20 + 6), btn.y + ((20 / 2) - (8 / 2)));
           }
       }
   }
   
   public void keyPressed(char var1) {
	   for(int i = 0; i < this.buttons.size(); ++i) {
    	   GuiObject btn = ((GuiObject)this.buttons.get(i));
    	   if (btn instanceof TextField) {
    		   ((TextField)btn).keyPressed(var1);
    	   }
       }
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
