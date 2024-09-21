package com.emirenesgames.engine.tools;


import com.emirenesgames.engine.DikenEngine;

public class ChatAllowedCharacters
{
    /**
     * This String have the characters allowed in any text drawing of minecraft.
     */
    public static final String allowedCharacters = getAllowedCharacters();

    /**
     * Array of the special characters that are allowed in any text drawing of Minecraft.
     */
    public static final char[] allowedCharactersArray = new char[] {'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

    /**
     * Load the font.txt resource file, that is on UTF-8 format. This file contains the characters that minecraft can
     * render Strings on screen.
     */
    private static String getAllowedCharacters()
    {
        return DikenEngine.getEngine().defaultFont.charTypes;
    }

    public static final boolean isAllowedCharacter(char par0)
    {
        return par0 != 167 && (allowedCharacters.indexOf(par0) >= 0 || par0 > 32);
    }

    /**
     * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
     */
    public static String filerAllowedCharacters(String par0Str)
    {
        StringBuilder var1 = new StringBuilder();
        char[] var2 = par0Str.toCharArray();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            char var5 = var2[var4];

            if (isAllowedCharacter(var5))
            {
                var1.append(var5);
            }
        }

        return var1.toString();
    }
}