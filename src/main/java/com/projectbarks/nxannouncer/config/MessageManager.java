package com.projectbarks.nxannouncer.config;

import com.projectbarks.nxannouncer.commands.Msg;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Brandon Barker
 */
public class MessageManager extends AbstractConfig {

    private String name;
    private List<Msg> messages;

    public MessageManager(Plugin plugin) {
        this(new File(plugin.getDataFolder(), "commands.yml"));
    }

    public MessageManager(File file) {
        super(file);
        this.messages = new ArrayList<Msg>();
    }

    protected void loadConfig() {
        Bukkit.getLogger().log(Level.INFO, "Loading {0} messages", Msg.values().length);

        name = this.getConfig().getString("name", "&8[&3NXA&8]&7");
        name = ChatColor.translateAlternateColorCodes('&', name);

        for (Msg key : Msg.values()) {
            String cmsg = this.getConfig().getString("Messages." + key.getName(), key.getMsg());
            key.setMsg(cmsg);
            this.messages.add(key);
        }
        Bukkit.getLogger().log(Level.INFO, "Message Loading Completed");
    }

    protected void saveConfig() {
        this.getConfig().set("name", name);

        for (Msg msg : this.messages) {
            this.getConfig().set("Messages." + msg.getName(), msg.getMsg());
        }
    }

    public String getMsg(Msg format) {
        Msg target = format;
        for (Msg msg : this.messages) {
            if (msg.getName().equalsIgnoreCase(format.getName())) {
                target = msg;
            }
        }
        String colorMsg = ChatColor.translateAlternateColorCodes('&', name + " " + target.getMsg());
        return colorMsg;
    }

    public String getMsg(Msg format, Object... args) {
        Msg target = format;
        for (Msg msg : this.messages) {
            if (msg.getName().equalsIgnoreCase(format.getName())) {
                target = msg;
            }
        }
        String newMsg = String.format(target.getMsg(), args);
        String colorMsg = ChatColor.translateAlternateColorCodes('&', name + " " + newMsg);
        return colorMsg;
    }

    public String getName() {
        return this.name;
    }

    public void msg(CommandSender sender, Msg message) {
        sender.sendMessage(this.getMsg(message));
    }

    public void msg(CommandSender sender, Msg message, Object... args) {
        sender.sendMessage(this.getMsg(message, args));
    }

    public void broadcast(Msg message) {
        Bukkit.getServer().broadcastMessage(this.getMsg(message));
    }

    public void broadcast(Msg message, Object... args) {
        Bukkit.getServer().broadcastMessage(this.getMsg(message, args));
    }
}