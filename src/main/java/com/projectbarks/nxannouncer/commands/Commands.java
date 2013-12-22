package com.projectbarks.nxannouncer.commands;

import com.projectbarks.nxannouncer.NXAnnouncer;
import com.projectbarks.nxannouncer.announcer.Announcement;
import com.projectbarks.nxannouncer.announcer.Timer;
import com.projectbarks.nxannouncer.config.Config;
import com.projectbarks.nxannouncer.config.MessageManager;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * @author Brandon Barker
 */
@Log
public class Commands implements CommandExecutor {

    private final MessageManager mm;
    private final Config config;
    private final NXAnnouncer nxa;

    public Commands(NXAnnouncer main) {
        this.mm = main.getMm();
        this.config = main.getConf();
        this.nxa = main;

    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        String cName = cmd.getName();
        if (cName.equalsIgnoreCase("announcer") || cName.equalsIgnoreCase("nxannouncer")) {
            if (args.length > 0) {
                String function = args[0];
                if (function.equalsIgnoreCase("help") || function.equalsIgnoreCase("?")) {
                    mm.msg(cs, Msg.HELP);
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "help", "List of commands");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "list", "List of announcements");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "view", "See an announcement");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "broadcast", "Force broadcast an announcement");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "reload", "Reload the config");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "previous", "See the last announcement");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "next", "Preview the next announcement");
                    mm.msg(cs, Msg.HELP_COMMANDS, cName, "setnext", "Set the next announcement");
                } else if (function.equalsIgnoreCase("list")) {
                    mm.msg(cs, Msg.LIST_COMMAND);
                    int i = 0;
                    for (Announcement announcement : this.config.getAnnouncements()) {
                        mm.msg(cs, Msg.LIST_ANNOUNCEMENT, i, announcement.getTranslatedMessage().get(0));
                        i++;
                    }
                } else if (function.equalsIgnoreCase("view")) {
                    if (args.length > 1) {
                        int location;
                        try {
                            location = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            mm.msg(cs, Msg.NUMBER_EXCEPTION, "2");
                            return true;
                        }
                        if (location > -1 && location < config.getAnnouncements().size()) {
                            mm.msg(cs, Msg.VIEW_INFO, location + "");
                            Announcement announcement = config.getAnnouncements().get(location);
                            this.view(announcement, cs);
                        } else {
                            mm.msg(cs, Msg.RANGE_EXCEPTION, 0, config.getAnnouncements().size() - 1);
                        }
                    } else {
                        mm.msg(cs, Msg.COMMAND_USAGE, "/" + cmd.getName() + " view &8[&3announcement&8]");
                    }
                } else if (function.equalsIgnoreCase("broadcast")) {
                    if (args.length > 1) {
                        int location;
                        try {
                            location = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            mm.msg(cs, Msg.NUMBER_EXCEPTION, "2");
                            return true;
                        }
                        if (location > -1 && location < config.getAnnouncements().size()) {
                            mm.msg(cs, Msg.VIEW_INFO, location + "");
                            Announcement announcement = config.getAnnouncements().get(location);
                            broadcast(Announcement.colorize(announcement.getColorizedHeader()));
                            for (String message : announcement.getTranslatedMessage()) {
                                broadcast(Announcement.colorize(message));
                            }

                            broadcast(Announcement.colorize(announcement.getColorizedFooter()));
                        } else {
                            mm.msg(cs, Msg.RANGE_EXCEPTION, 0, config.getAnnouncements().size() - 1);
                        }
                    } else {
                        mm.msg(cs, Msg.COMMAND_USAGE, "/" + cmd.getName() + " broadcast &8[&3announcement&8]");
                    }
                } else if (function.equalsIgnoreCase("reload")) {
                    config.reload();
                    nxa.getTimer().setCount(0);
                    try {
                        nxa.getTimer().cancel();
                    } catch (Exception e) {
                        log.log(Level.WARNING, "Timer encountered Error!");
                    }
                    long interval = (long) (20L * config.getInterval());
                    nxa.setTimer(new Timer(nxa));
                    nxa.getTimer().runTaskTimer(nxa, interval, interval);
                    mm.msg(cs, Msg.CONFIG_RELOAD);
                } else if (function.equalsIgnoreCase("previous")) {
                    int location = nxa.getTimer().getCount();
                    if (location > 0) {
                        location -= 1;
                    } else {
                        location = config.getAnnouncements().size() - 1;
                    }
                    mm.msg(cs, Msg.VIEW_INFO, location);
                    view(config.getAnnouncements().get(location), cs);
                } else if (function.equalsIgnoreCase("next")) {
                    int location = nxa.getTimer().getCount() + 1;
                    if (location >= config.getAnnouncements().size()) {
                        location = 0;
                    }
                    mm.msg(cs, Msg.VIEW_INFO, location);
                    view(config.getAnnouncements().get(location), cs);
                } else if (function.equalsIgnoreCase("setNext")) {
                    if (args.length > 1) {
                        int location;
                        try {
                            location = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            mm.msg(cs, Msg.NUMBER_EXCEPTION, "2");
                            return true;
                        }
                        if (location > -1 && location < config.getAnnouncements().size()) {
                            this.nxa.getTimer().setCount(location);
                            mm.msg(cs, Msg.ANNOUNCEMENT_SET, location);
                        } else {
                            mm.msg(cs, Msg.RANGE_EXCEPTION, 0, config.getAnnouncements().size() - 1);
                        }
                    } else {
                        mm.msg(cs, Msg.COMMAND_USAGE, "/" + cmd.getName() + " setnext &8[&3announcement&8]");
                    }
                } else {
                    mm.msg(cs, Msg.COMMAND_USAGE, "/" + cmd.getName() + " &8[&3function&8]");
                }
            } else {
                mm.msg(cs, Msg.COMMAND_USAGE, "/" + cmd.getName() + " &8[&3function&8]");
            }
            return true;
        }
        return false;
    }

    private void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    private void view(Announcement announcement, CommandSender cs) {
        cs.sendMessage(announcement.getColorizedHeader());
        for (String message : announcement.getTranslatedMessage()) {
            cs.sendMessage(Announcement.colorize(message));
        }
        cs.sendMessage(announcement.getColorizedFooter());
    }
}
