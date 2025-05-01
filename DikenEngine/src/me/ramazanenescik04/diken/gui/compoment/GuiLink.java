package me.ramazanenescik04.diken.gui.compoment;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class GuiLink extends GuiCompoment {
	
 	public GuiLink(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
 	
 	public GuiLink(int x, int y, int width, int height, URI uri) {
		super(x, y, width, height);
		this.uri = uri;
	}
 	
	private static final long serialVersionUID = 1L;
 	private URI uri;
 	
 	protected void _setURI(URI uri) {
 		this.uri = uri;
 	}
 	
	private void _openLink() {
		if(uri == null) {
			uri = URI.create("about:blank");
		}
		
		try {
			if(Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				desktop.browse(uri);
			}
		} catch (IOException e) {
		}
	}

	public void mouseClicked(int x, int y, int button, boolean isTouch) {
		if(isTouch) {
			if(button == 0) {
				_openLink();
			}
		}
	}
	
}
