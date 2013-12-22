package com.projectbarks.nxannouncer.announcer;

import com.projectbarks.nxannouncer.FontManager;
import com.projectbarks.nxannouncer.NXAnnouncer;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
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
                    int cutLocation = maxLength;
                    while (cut.charAt(cutLocation) != ' ') {
                        cutLocation -= 1;
                    }
                    finalCut.add(cut.substring(0, cutLocation));
                    cut = cut.substring(cutLocation, cut.length());
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
        FontManager font = NXAnnouncer.getFont();
        if (message.contains(ChatColor.BOLD + "")) {
            size = font.getStringWidthBold(stripedColor);
        } else {
            size = font.getStringWidth(stripedColor);
        }

        Integer spacer = Math.round(size / font.getStringWidth(" "));
        Integer wrap = Math.round(((font.getStringWidth(newFooter)
                + font.getStringWidth(newHeader)) / 2)
                / font.getStringWidth(" "));
        spacer = Math.abs(wrap - spacer);
        spacer = Math.round(spacer / 2);

        String spaces = "";
        for (int i = 0; i < spacer; i++) {
            spaces += " ";
        }
        return spaces;
    }

    @Getter
    private final String message;
    @Getter
    private final String footer;
    @Getter
    private final String header;
    @Getter
    private final String colorizedHeader;
    @Getter
    private final String colorizedFooter;
    @Getter
    private final List<String> translatedMessage;

    public Announcement(String message, String footer, String header) {
        this.message = message;
        this.footer = footer;
        this.header = header;
        this.colorizedHeader = colorize(header);
        this.colorizedFooter = colorize(footer);
        this.translatedMessage = new ArrayList<String>();
        translatedMessage.addAll(stringTranslate(message, Math.round((header.length() + footer.length()) / 2)));
    }
}
