package me.ramazanenescik04.diken.gui.compoment;

import java.util.List;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.InputHandler;
import me.ramazanenescik04.diken.gui.Hitbox;
import me.ramazanenescik04.diken.gui.screen.DownBackground;
import me.ramazanenescik04.diken.gui.screen.IBackground;
import me.ramazanenescik04.diken.resource.Bitmap;

public class Panel extends GuiCompoment {
	private static final long serialVersionUID = 1L;
	private List<GuiCompoment> compoments;
	private IBackground background;

	public Panel(int x, int y, int width, int height) {
		super(x, y, width, height);
		compoments = new java.util.ArrayList<GuiCompoment>();
	}
	
	public void addCompoment(GuiCompoment compoment) {
		if (compoment instanceof Panel) {
			Panel panel = (Panel) compoment;
			panel.init(DikenEngine.getEngine());
		}
		this.compoments.add(compoment);
	}
	
	public void removeCompoment(GuiCompoment compoment) {
		this.compoments.remove(compoment);
	}
	
	public void clearCompoments() {
		this.compoments.clear();
	}
	
	public void removeCompoment(int index) {
		this.compoments.remove(index);
	}
	
	public GuiCompoment getCompoment(int index) {
		return this.compoments.get(index);
	}
	
	public int getCompomentCount() {
		return this.compoments.size();
	}

	public Bitmap render() {
		Bitmap bitmap = super.render();
		if (this.background != null) {
			this.background.render(bitmap);
		}
		for (GuiCompoment compoment : this.compoments) {
			bitmap.draw(compoment.render(), compoment.x, compoment.y);
		}
		return bitmap;
	}

	public void tick(DikenEngine engine) {
		if (this.background != null) {
			this.background.tick();
		}
		for (GuiCompoment compoment : this.compoments) {
			compoment.tick(engine);
		}
	}

	public void keyPressed(char var1, int var2) {
		for (GuiCompoment compoment : this.compoments) {
			if (active)
				compoment.keyPressed(var1, var2);
		}
	}

	public void mouseClicked(int x, int y, int button, boolean isTouch2) {
		for (GuiCompoment compoment : this.compoments) {
			boolean isTouch = false;
			Hitbox rect = InputHandler.getMouseHitbox();
			Hitbox aarect = new Hitbox(rect.x - this.x, rect.y - this.y, rect.width, rect.height);
			if (compoment.intersects(aarect) && isTouch2) {
				isTouch = true;
			}
			
			if (active)
				compoment.mouseClicked(this.x - x, this.y - y, button, isTouch);
		}
	}

	public void setBackground(IBackground downBackground) {
		this.background = downBackground;
	}
	
	public void init(DikenEngine engine) {
	}

	public void mouseGetInfo(int x, int y, boolean isTouch2) {
		for (GuiCompoment compoment : this.compoments) {
			Hitbox rect = InputHandler.getMouseHitbox();
			boolean isTouch = false;
			Hitbox aarect = new Hitbox(rect.x - this.x, rect.y - this.y, rect.width, rect.height);
			if (compoment.intersects(aarect) && isTouch2) {
				isTouch = true;
			}
			
			compoment.mouseGetInfo(this.x - x, this.y - y, isTouch);
		}
	}
}
