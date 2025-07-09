package me.ramazanenescik04.diken.dev;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.lang.reflect.Field;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.compoment.Button;
import me.ramazanenescik04.diken.gui.compoment.GuiCompoment;
import me.ramazanenescik04.diken.gui.compoment.ImageButton;
import me.ramazanenescik04.diken.gui.compoment.LinkButton;
import me.ramazanenescik04.diken.gui.compoment.LinkText;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.gui.compoment.RenderImage;
import me.ramazanenescik04.diken.gui.compoment.Text;
import me.ramazanenescik04.diken.gui.compoment.TextField;
import me.ramazanenescik04.diken.gui.screen.Screen;
import me.ramazanenescik04.diken.resource.Bitmap;
import me.ramazanenescik04.diken.resource.IOResource;

public class GuiEditorScreen extends Screen {

	private Screen parent;
	private Panel editorPanel = new EditorPanel(2, 2, 200, 200);
	
	protected static GuiCompoment selectedCompoment;
	
	protected static boolean isCompomentEditFieldMode = false;
	protected static boolean deleteMode = false;
	
	public GuiEditorScreen(Screen parent) {
		this.parent = parent;
	}
	
	public String generateCode() {
		StringBuilder code = new StringBuilder();
		code.append("import me.ramazanenescik04.diken.gui.screen.*;\n");
		code.append("\n");
		code.append("public class GeneratedGui extends Screen {\n");
		code.append("\n");
		code.append("	public void openScreen() {\n");
		
		code.append("		this.getContentPane().clearCompoments();\n");
		for (int i = 0; i < editorPanel.count(); i++) {
			code.append("		this.getContentPane().addCompoments(" + generateCompomentCode(editorPanel.get(i)) + ");\n");
		}
		
		code.append("	}\n");
		code.append("\n");
		code.append("}\n");
		
		return code.toString();
	}
	
	public String generateCompomentCode(GuiCompoment compoment) {
		if (compoment instanceof Panel) {
			((Panel) compoment).drawX = false;
		}
		Class<?> clazz = compoment.getClass();
        String className = clazz.getSimpleName();
		
		StringBuilder code = new StringBuilder();
		code.append("new " + className + "( \"-- İstediğini Yap! ama sadece x, y, w ve h değeri olcak! --\", " + compoment.x + ", " + compoment.y + ", " + compoment.width + ", " + compoment.height + ")");
		
		return code.toString();
		
	}
	
	public void render(Bitmap bitmap) {
		super.render(bitmap);
		Text.render("Total Compoments: " + editorPanel.count() + ", Active Modes: " + (isCompomentEditFieldMode ? "ObjectFieldEdit, " : "") + (deleteMode ? "DeleteMode, " : ""), bitmap, 2, editorPanel.height + 28);
	}

	public void openScreen() {		
		editorPanel.setSize(engine.getWidth() - 108, engine.getHeight() - 40);
		
		this.getContentPane().clear();
		this.getContentPane().add(editorPanel);
		this.getContentPane().add(new Button("generateCode", 2, editorPanel.height + 2, 100, 20).setRunnable(() -> {
			this.engine.setCurrentScreen(new ShowGeneratedCodeScreen(this, generateCode()));
			return;
		}));
		this.getContentPane().add(new Button("back", 102, editorPanel.height + 2, 100, 20).setRunnable(() -> {
			this.engine.setCurrentScreen(parent);
		}));
		
		this.getContentPane().add(new Button("clear", 202, editorPanel.height + 2, 100, 20).setRunnable(() -> {
			this.editorPanel.clear();
		}));
		
		this.getContentPane().add(new Button("button", engine.getWidth() - 106, 2, 49, 20).setRunnable(() -> {
			selectedCompoment = new Button("newButton", 0, 0, 100, 20);
		}));
		
		this.getContentPane().add(new Button("label", engine.getWidth() - 56, 2, 49, 20).setRunnable(() -> {
			selectedCompoment = new Text("newLabel", 0, 0);
		}));
		
		this.getContentPane().add(new Button("panel", engine.getWidth() - 106, 26, 49, 20).setRunnable(() -> {
			Panel panel = new Panel(0, 0, 100, 100);
			panel.drawX = true;
			selectedCompoment = panel;
		}));
		
		this.getContentPane().add(new Button("image", engine.getWidth() - 56, 26, 49, 20).setRunnable(() -> {
			selectedCompoment = new RenderImage(IOResource.missingTexture, 0, 0);
		}));
		
		this.getContentPane().add(new Button("textField", engine.getWidth() - 106, 50, 100, 20).setRunnable(() -> {
			selectedCompoment = new TextField("newTextField", 0, 0, 100, 20);
		}));
		
		this.getContentPane().add(new Button("linkButton", engine.getWidth() - 106, 74, 100, 20).setRunnable(() -> {
			selectedCompoment = new LinkButton("newLinkButton", 0, 0, 100, 20);
		}));
		
		this.getContentPane().add(new Button("linkText", engine.getWidth() - 106, 98, 100, 20).setRunnable(() -> {
			selectedCompoment = new LinkText("newLinkText", 0, 0);
		}));
		
		this.getContentPane().add(new Button("imageButton", engine.getWidth() - 106, 122, 100, 20).setRunnable(() -> {
			selectedCompoment = new ImageButton(IOResource.missingTexture, 0, 0, 100, 20);
		}));
		
		/*this.getContentPane().addCompoment(new Button("objectProperties", engine.getWidth() - 106, 146, 100, 15).setRunnable(() -> {
			isCompomentEditFieldMode = !isCompomentEditFieldMode;
		}));*/
		
		this.getContentPane().add(new Button("deleteCompoment", engine.getWidth() - 106, 162, 100, 15).setRunnable(() -> {
			deleteMode = !deleteMode;
		}));
		
	}
	
	public static class EditorPanel extends Panel {
		private static final long serialVersionUID = 1L;
		
		public EditorPanel(int x, int y, int width, int height) {
			super(x, y, width, height);
			/*Bitmap colorBitmap = Bitmap.createClearedBitmap(32, 32, 0xff000000);
			StaticBackground background = new StaticBackground(colorBitmap);
			this.setBackground(background);*/
		}
		
		public void init(DikenEngine engine) {
		}

		public void mouseClicked(int x, int y, int button, boolean isTouch2) {
			super.mouseClicked(x, y, button, isTouch2);
			
			if (deleteMode) {
				if (isTouch2) {
					this.remove(this.get(new Point(-x, -y)));
				}
			} else if (isCompomentEditFieldMode) {
				if (selectedCompoment != null)
					selectedCompoment = null;
				
				if (isTouch2)
					DikenEngine.getEngine().setCurrentScreen(new ObjectPropertiesScreen(DikenEngine.getEngine().getCurrentScreen(), this.get(new Point(-x, -y))));
				
			} else {
				if(selectedCompoment != null && isTouch2) {
					this.add(selectedCompoment);
					selectedCompoment = null;
				}
			}
			
		}

		public void mouseGetInfo(int x, int y, boolean isTouch2) {
			super.mouseGetInfo(x, y, isTouch2);
			
			if(selectedCompoment != null && isTouch2) {
				selectedCompoment.setLocation(-x, -y);
			}
		}

		public Bitmap render() {
			Bitmap bitmap = Bitmap.createClearedBitmap(width, height, 0xff000000);
			bitmap.box(0, 0, width - 1, height - 1, 0xffffffff);
			
			Text.render("XY: 0, 0", bitmap, 2, 2);
			Text.render("XY: " + width + ", 0", bitmap, width - 65, 2);
			bitmap.drawLine(0, height / 2, width, height / 2, 0xffffff00, 1);
			
			Text.render("XY: 0, " + height, bitmap, 2, height - 10);
			Text.render("XY: " + width + ", " + height, bitmap, width - 80, height - 10);
			bitmap.drawLine(width / 2, 0, width / 2, height, 0xffffff00, 1);
			
			if (selectedCompoment != null) {
				bitmap.draw(selectedCompoment.render(), (int)selectedCompoment.getX(), (int)selectedCompoment.getY());
			}
			
			bitmap.draw(super.render(), 0, 0);
			return bitmap;
		}
		
	}
	
	private static class ShowGeneratedCodeScreen extends Screen {
		private Screen parent;
		private String code;
		
		public ShowGeneratedCodeScreen(Screen parent, String code) {
			this.parent = parent;
			this.code = code;
		}
		
		public void openScreen() {
			this.getContentPane().add(new Button("back", 2, 202, 100, 20).setRunnable(() -> {
				this.engine.setCurrentScreen(parent);
			}));
			
			this.getContentPane().add(new Button("copy", 102, 202, 100, 20).setRunnable(() -> {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new java.awt.datatransfer.StringSelection(code), null);
			}));
		}
		
		public void render(Bitmap bitmap) {
			super.render(bitmap);
			Text.render("-- Generated Code", bitmap, 2, 2);
			Text.render("-- Press 'Copy' to copy the code", bitmap, 2, 18);
			
			Text.render(code, bitmap, 2, 38, 0xffffff00);
		}
		
	}
	
	private static class ObjectPropertiesScreen extends Screen {
		private Screen parent;
		private GuiCompoment compoment;
		
		public ObjectPropertiesScreen(Screen parent, GuiCompoment compoment) {
			this.parent = parent;
			this.compoment = compoment;
		}
		
		public void openScreen() {
			this.getContentPane().add(new Button("//back", 2, 202, 100, 20).setRunnable(() -> {
				int i = 1;
				for (Field field : compoment.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					try {
						Object value = ((TextField)this.getContentPane().get(i)).text;
						field.set(compoment, value);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					i += 2;
				}
				
				this.engine.setCurrentScreen(parent);
			}));
			
			this.getContentPane().add(new Button("dontSaveBack", 104, 202, 100, 20).setRunnable(() -> {
				this.engine.setCurrentScreen(parent);
			}));
			
			if (compoment != null) {
				Class<?> clazz = compoment.getClass();
				Field[] fields = clazz.getDeclaredFields();
				int y = 2;
				for (Field field : fields) {
		            try {
		                field.setAccessible(true);
		                Object value = field.get(compoment);
		                this.getContentPane().add(new Text(field.getName() + ": ", 2, y, 0xffffff00));
		                this.getContentPane().add(new TextField("" + value, 2 + (field.getName().length() * 7), y, 200, 20));
		            } catch (IllegalAccessException e) {
		                ;
		            }
		            y += 24;
		        }
			}
			
		}

	}
}
