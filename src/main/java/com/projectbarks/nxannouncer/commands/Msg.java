package com.projectbarks.nxannouncer.commands;

import java.util.Locale;

/**
 *
 * @author Brandon Barker
 */
public enum Msg {

    COMMAND_USAGE("&eIncorrect format! Use:&o %s"),
    HELP("&bHelp&7: "),
    HELP_COMMANDS("&l/%s &r&7%s -&b %s"),
    LIST_COMMAND("&3&lAnnouncements: "),
    LIST_ANNOUNCEMENT("&8[&3%s&8]:&7 %s"),
    RANGE_EXCEPTION("&eNumber must be in range from %s to %s"),
    VIEW_INFO("Announcement &8[&3%s&8]"),
    NUMBER_EXCEPTION("Argument &8[&3%s&8] must be numeric!"),
    ANNOUNCEMENT_SET("Next announcement set to &8[&3%s&8]s"),
    CONFIG_RELOAD("successfully reloaded config!");
    private String message;

    Msg(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.toString().toUpperCase(Locale.getDefault());
    }

    /**
     *
     * @return
     */
    public String getMsg() {
        return this.message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}
