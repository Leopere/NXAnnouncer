package com.projectbarks.nxannouncer;

import java.io.IOException;
import java.io.InputStream;
import org.bukkit.plugin.Plugin;

public class Font {

    private static Integer[] widths;

    public static void load(Plugin plugin) {
        widths = new Integer[0xFFFF];
        try {
            InputStream in = plugin.getResource("font.bin");
            for (int i = 0; i < getWidths().length; i++) {
                widths[i] = in.read();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getStringWidth(String str) {
        int width = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ') {
                width += 4;
            } else {
                width += getWidths()[c] + 1;
            }
        }
        return width;
    }

    public static int getStringWidthBold(String str) {
        int width = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ') {
                width += 4;
            } else {
                width += getWidths()[c] + 2;
            }
        }
        return width;
    }

    /**
     * @return the widths
     */
    public static Integer[] getWidths() {
        return widths.clone();
    }

    private Font() {
    }
}