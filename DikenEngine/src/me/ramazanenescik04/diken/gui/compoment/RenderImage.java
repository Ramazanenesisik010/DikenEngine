package me.ramazanenescik04.diken.gui.compoment;

import me.ramazanenescik04.diken.resource.Bitmap;

public class RenderImage extends GuiCompoment {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bitmap bitmap;

	public RenderImage(Bitmap btp, int x, int y) {
		super(x, y, btp.w, btp.h);
		this.bitmap = btp;
	}
	
	public Bitmap render() {
		return bitmap;
	}

}
