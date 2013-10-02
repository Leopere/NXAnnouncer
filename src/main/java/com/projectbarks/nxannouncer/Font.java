package com.projectbarks.nxannouncer;

import java.io.IOException;
import java.io.InputStream;
import org.bukkit.plugin.Plugin;

public class Font implements Runnable {

    private Integer[] widths;
    private boolean loaded;
    private InputStream in;

    public Font(InputStream in) {
        this.loaded = false;
        this.in = in;
    }
    
    @Override
    public void run() {
        this.load();
    }

    private void load() {
        
        widths = new Integer[0xFFFF];
        try {
            for (int i = 0; i < getWidths().length; i++) {
                widths[i] = in.read();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loaded = true;
    }

    public int getStringWidth(String str) {
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

    public int getStringWidthBold(String str) {
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
    public Integer[] getWidths() {
        return widths.clone();
    }

    public boolean isLoaded() {
        return loaded;
    }
}