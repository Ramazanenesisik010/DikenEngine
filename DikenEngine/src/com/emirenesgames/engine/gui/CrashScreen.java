package com.emirenesgames.engine.gui;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.emirenesgames.engine.Bitmap;
import com.emirenesgames.engine.DikenEngine;
import com.emirenesgames.engine.Language;

public class CrashScreen extends Screen {

    private Exception e;
    private String message = "";
    private Language language = Language.i;
    private String[] messageLines;
    
    public CrashScreen(Exception e) {
        this.e = e;
    }
    
    public void openScreen() {
        StringWriter sWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(sWriter));
        message = sWriter.toString();
        
        message += "\n" + language.languageValue("legacycrash.issusegithub") + " \n\n " + language.languageValue("legacycrash.exit");
        
        // Split the stack trace into lines
        messageLines = message.split("\n");
        
        this.buttons.clear();
    }
    
    public void render(Bitmap screen) {
        screen.clear(0xffff0000);
        
        // Render the title
        Text.renderCenter(language.languageValue("legacycrash.title"), screen, (DikenEngine.WIDTH / 2), 2);
        
        // Render each line of the stack trace separately
        int xPos = (DikenEngine.WIDTH / 2) - (Text.stringBitmapAverageWidth(messageLines, engine.defaultFont) / 2);
        int yPos = 20;
        int lineHeight = 10; // Adjust based on your font height
        
        for (String line : messageLines) {
            Text.render(line, screen, xPos, yPos);
            yPos += lineHeight;
        }
        
        //Text.render("\nLütfen Github'a Bildiriniz!", screen, (DikenEngine.WIDTH / 4), yPos);
    }
}