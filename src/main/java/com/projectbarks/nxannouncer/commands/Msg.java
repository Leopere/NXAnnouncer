package com.projectbarks.nxannouncer.commands;

import java.util.Locale;

/**
 *
 * @author Brandon Barker
 */
public enum Msg {

    COMMAND_USAGE("&eIncorrect format! Use:&o %s"),
    HELP("&bHelp&7: "),
    HELP_COMMANDS("&l/%s &r&7%s -&b %s");

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
