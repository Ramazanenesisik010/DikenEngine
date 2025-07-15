package me.ramazanenescik04.diken.gui.compoment;

import java.util.*;

import me.ramazanenescik04.diken.DikenEngine;
import me.ramazanenescik04.diken.gui.UniFont;
import me.ramazanenescik04.diken.resource.Bitmap;

public class TextLine extends GuiCompoment {
	
	private static final long serialVersionUID = 1L;
	private boolean isFocused = false;
	private boolean editable = true;
	
	private List<String> textLines = new ArrayList<>();
	private UniFont font = DikenEngine.getEngine().defaultFont;
	private int color = 0xffffffff, bgColor = 0xff484848; // Default text color is white

	public TextLine(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	//API START
	
	public boolean isFocused() {
		return isFocused;
	}
	
	public TextLine setFocused(boolean isFocused) {
		this.isFocused = isFocused;
		return this;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public TextLine setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}
	
	public List<String> getTextLines() {
		return textLines;
	}
	
	public TextLine setTextLines(List<String> textLines) {
		this.textLines = textLines;
		return this;
	}
	
	public TextLine add(String textLine) {
		this.textLines.add(textLine);
		return this;
	}
	
	public TextLine remove(String textLine) {
		this.textLines.remove(textLine);
		return this;
	}
	
	public TextLine clear() {
		this.textLines.clear();
		return this;
	}
	
	public String getText() {
		return String.join("\n", textLines);
	}
	
	public TextLine setText(String text) {
		this.textLines.clear();
		if (text != null && !text.isEmpty()) {
			this.textLines.addAll(Arrays.asList(text.split("\n")));
		}
		return this;
	}
	
	public UniFont getFont() {
		return font;
	}
	
	public TextLine setFont(UniFont font) {
		this.font = font;
		return this;
	}
	
	public int getColor() {
		return color;
	}
	
	public TextLine setColor(int color) {
		this.color = color;
		return this;
	}
	
	public int getBgColor() {
		return bgColor;
	}
	
	public TextLine setBgColor(int bgColor) {
		this.bgColor = bgColor;
		return this;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof TextLine)) return false;
		TextLine other = (TextLine) obj;
		return this.isFocused == other.isFocused &&
				this.editable == other.editable &&
				this.textLines.equals(other.textLines);
	}
	
	public int hashCode() {
		return Objects.hash(isFocused, editable, textLines);
	}
	
	public String toString() {
		return "TextLine{" +
				"isFocused=" + isFocused +
				", editable=" + editable +
				", textLines=" + textLines +
				'}';
	}
	
	public TextLine clone() {
		TextLine cloned = new TextLine(x, y, width, height);
		cloned.setFocused(this.isFocused);
		cloned.setEditable(this.editable);
		cloned.setTextLines(new ArrayList<>(this.textLines));
		return cloned;
	}
	
	// API END

	@Override
	public Bitmap render() {
		Bitmap bitmap = super.render();
		bitmap.clear(bgColor);
		
		bitmap.box(0, 0, width - 1, height - 1, isFocused() ? 0xffffff00 : 0xffffffff);
		
		//Render Text Lines
		for (int i = 0; i < textLines.size(); i++) {
			String line = textLines.get(i);
			int averageHeight = Text.stringBitmapAverageHeight(line, DikenEngine.getEngine().defaultFont);
			int yOffset = 2 + (i * averageHeight); // Assuming each line is 12 pixels tall
			if (yOffset < height - 2) { // Ensure we don't draw outside the bounds
				Text.render(line, bitmap, 2, yOffset);
			}
		}
		return bitmap;
	}

	@Override
	public void tick(DikenEngine engine) {
	}

	@Override
	public void keyPressed(char var1, int var2) {
	}

	@Override
	public void mouseClicked(int x, int y, int button, boolean isTouch) {
		if (isTouch && button == 0) {
			if (editable) {
				this.isFocused = !this.isFocused; // Toggle focus on click
			} else {
				this.isFocused = false; // If not editable, lose focus
			}
		} else {
			this.isFocused = false; // Lose focus on other button clicks
		}
	}

	@Override
	public void mouseGetInfo(int x, int y, boolean isTouch) {
	}
}
