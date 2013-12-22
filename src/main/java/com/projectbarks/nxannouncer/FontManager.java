package com.projectbarks.nxannouncer;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;

public class FontManager extends BukkitRunnable {

    @Getter
    private Integer[] widths;
    @Getter
    private boolean loaded;
    private InputStream in;
    @Getter
    private AnnouncerFinalizer finishEnable;
    private NXAnnouncer plugin;

    public FontManager(InputStream in, NXAnnouncer plugin) {
        this.loaded = false;
        this.in = in;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.load();
        finishEnable = new AnnouncerFinalizer(plugin);
        finishEnable.runTaskLater(plugin, 1L);
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
}