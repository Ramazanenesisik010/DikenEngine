package me.ramazanenescik04.diken.gui.screen;

import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.resource.*;
import me.ramazanenescik04.diken.resource.ResourceLocator;

public class DefaultLoadingScreen extends LoadingScreen {

	public void render(Bitmap screen) {
		super.render(screen);
		Bitmap icon = (Bitmap) ResourceLocator.getResource("icon-x16");
		Text.render("Powered By:", screen, (engine.getWidth() / 2) - (icon.w / 2), (engine.getHeight() / 2) - ((icon.h / 2) * 2));
		Text.render("DikenEngine", screen, (engine.getWidth() / 2) - (icon.w / 2), (engine.getHeight() / 2) - ((icon.h / 2) * 1));
		screen.draw(icon, ((engine.getWidth() / 2) - (icon.w / 2)) - 33, (engine.getHeight() / 2) - 33);
	}

	public void openScreen() {
		ArrayBitmap icon = (ArrayBitmap) ResourceLocator.getResource("bgd-tiles");
		this.setBackground(new DownBackground(icon.bitmap[4][0]));
	}

}
