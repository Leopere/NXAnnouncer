package com.projectbarks.nxannouncer.announcer;

import com.projectbarks.nxannouncer.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;

/**
 *
 * @author Brandon Barker
 */
public class Announcement {

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> stringTranslate(String start, int maxLength) {
        List<String> finalCut = new ArrayList<String>();
        for (String s : start.split("%[nN]")) {
            String cut = s;
            if (cut.contains(" ")) {
                while (cut.length() > maxLength) {
                    int cutlocation = maxLength;
                    while (cut.charAt(cutlocation) != ' ') {
                        cutlocation -= 1;
                    }
                    finalCut.add(cut.substring(0, cutlocation));
                    cut = cut.substring(cutlocation, cut.length());
                }
            }
            finalCut.add(cut);
        }
        return finalCut;
    }

    public static String formatChar(String message, String header, String footer) {
        int size;
        String stripedColor = ChatColor.stripColor(message);
        String newHeader = ChatColor.stripColor(header);
        String newFooter = ChatColor.stripColor(footer);
        if (message.contains(ChatColor.BOLD + "")) {
            size = Font.getStringWidthBold(stripedColor);
        } else {
            size = Font.getStringWidth(stripedColor);
        }

        Integer spacer = Math.round(size / Font.getStringWidth(" "));
        Integer wrap = Math.round(((Font.getStringWidth(newFooter) + Font.getStringWidth(newHeader)) / 2) / Font.getStringWidth(" "));
        spacer = Math.abs(wrap - spacer);
        spacer = Math.round(spacer / 2);

        String spaces = "";
        for (int i = 0; i < spacer; i++) {
            spaces += " ";
        }
        return spaces;
    }
    private final String message;
    private final String suffixWrapper;
    private final String prefixWrapper;
    private final String cPrefixWrapper;
    private final String cSuffixWrapper;
    private final List<String> translated;

    public Announcement(String message, String footer, String header) {
        this.message = message;
        this.suffixWrapper = footer;
        this.prefixWrapper = header;
        this.cPrefixWrapper = colorize(prefixWrapper);
        this.cSuffixWrapper = colorize(suffixWrapper);
        this.translated = new ArrayList<String>();
        translated.addAll(stringTranslate(message, Math.round((prefixWrapper.length() + suffixWrapper.length()) / 2)));
    }

    public String getMessage() {
        return message;
    }

    public String getFooter() {
        return suffixWrapper;
    }

    public String getHeader() {
        return prefixWrapper;
    }

    public String getColorizedHeader() {
        return cPrefixWrapper;
    }

    public String getColorizedFooter() {
        return cSuffixWrapper;
    }

    public List<String> getTranslatedMessage() {
        return this.translated;
    }
}
