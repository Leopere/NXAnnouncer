package com.projectbarks.nxannouncer.commands;

import com.projectbarks.nxannouncer.config.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Brandon Barker
 */
public class Commands implements CommandExecutor {

    private MessageManager mm;

    public Commands(MessageManager mm) {
        this.mm = mm;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        String cName = cmd.getName();
        if (cName.equalsIgnoreCase("announcer") || cName.equalsIgnoreCase("BarksSimpleAnnouncer")) {
            if (args.length > 0) {
                String function = args[0];
                if (function.equalsIgnoreCase("help")) {
                    mm.msg(cs, Msg.HELP);
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "help", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "list", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "view", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "broadcast", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "reload", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "previous", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "next", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "next", "List of commands");
                } else if (function.equalsIgnoreCase("list")) {
                } else if (function.equalsIgnoreCase("view")) {
                } else if (function.equalsIgnoreCase("broadcast")) {
                } else if (function.equalsIgnoreCase("reload")) {
                } else if (function.equalsIgnoreCase("previous")) {
                } else if (function.equalsIgnoreCase("next")) {
                } else if (function.equalsIgnoreCase("setNext")) {
                }
            } else {
                mm.msg(cs, Msg.COMMAND_USAGE, "/" + cmd.getName() + " [function]");
            }
            return true;
        }
        return false;
    }
}
