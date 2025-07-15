package me.ramazanenescik04.diken.gui.window;

import org.lwjgl.input.Keyboard;

import me.ramazanenescik04.diken.gui.compoment.Button;
import me.ramazanenescik04.diken.gui.compoment.Panel;
import me.ramazanenescik04.diken.gui.compoment.TextField;
import me.ramazanenescik04.diken.gui.compoment.TextLine;

public class ConsoleWindow extends Window {
	private static final long serialVersionUID = 1L;

	public ConsoleWindow(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setTitle("Console");
		this.resizable = true;
	}

	protected void open() {
		Panel contentPane = this.getContentPane();
		TextLine textLine = new TextLine(0, 0, contentPane.width, contentPane.height - 20);
		textLine.setEditable(false);
		textLine.setFocused(false);
		textLine.setActive(false);
		contentPane.add(textLine);
		
		TextField textField = new TextField(0, contentPane.height - 20, contentPane.width - 50, 20);
		
		Button sendButton = new Button("Gönder", 0, contentPane.height - 20, 50 - 4, 16).setRunnable(() -> {
			String text = textField.text;
			if (!text.isEmpty()) {
				textLine.add(text);
				textField.text = "";
			}
		});
		
		contentPane.add(sendButton);
		contentPane.add(textField);
	}

	public void resized() {
		super.resized();
		
		Panel contentPane = this.getContentPane();
		TextLine textLine = (TextLine) contentPane.get(0);
		textLine.setSize(contentPane.width, contentPane.height - 20);
		
		Button sendButton = (Button) contentPane.get(1);
		sendButton.setLocation(contentPane.width - 50, contentPane.height - 20);
		
		TextField textField = (TextField) contentPane.get(2);
		textField.setLocation(0, contentPane.height - 20);
		textField.setSize(contentPane.width - 50, 20);
	}

	public void keyPressed(char var1, int var2) {
		super.keyPressed(var1, var2);
		
		if (var2 == Keyboard.KEY_RETURN || var2 == Keyboard.KEY_NUMPADENTER) {
			Panel contentPane = this.getContentPane();
			TextField textField = (TextField) contentPane.get(2);
			TextLine textLine = (TextLine) contentPane.get(0);
			
			String text = textField.text;
			if (!text.isEmpty()) {
				textLine.add(text);
				textField.text = "";
			}
		}
	}
}
