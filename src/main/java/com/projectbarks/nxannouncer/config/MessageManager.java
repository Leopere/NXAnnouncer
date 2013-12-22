package com.projectbarks.nxannouncer.config;

import com.projectbarks.nxannouncer.commands.Msg;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Brandon Barker
 */
public class MessageManager extends AbstractConfig {

    @Getter
    private String name;
    private List<Msg> messages;
    private String printName;

    public MessageManager(Plugin plugin) {
        this(new File(plugin.getDataFolder(), "commands.yml"), plugin.getName());
    }

    public MessageManager(File file, String name) {
        super(file);
        this.messages = new ArrayList<Msg>();
        this.printName = name;
    }

    protected void loadConfig() {
        Bukkit.getLogger().log(Level.INFO, "[{0}] Loading {1} command Message", new Object[]{printName, Msg.values().length});

        name = this.getConfig().getString("name", "&8[&3NXA&8]&7");
        name = ChatColor.translateAlternateColorCodes('&', name);

        for (Msg key : Msg.values()) {
            String cmsg = this.getConfig().getString("Messages." + key.getName(), key.getMessage());
            key.setMessage(cmsg);
            this.messages.add(key);
        }
        Bukkit.getLogger().log(Level.INFO, "Command messages loading completed");
    }

    protected void saveConfig() {
        this.getConfig().set("name", name);

        for (Msg msg : this.messages) {
            this.getConfig().set("Messages." + msg.getName(), msg.getMessage());
        }
    }

    public String getMessage(Msg format) {
        Msg target = format;
        for (Msg msg : this.messages) {
            if (msg.getName().equalsIgnoreCase(format.getName())) {
                target = msg;
            }
        }
        return ChatColor.translateAlternateColorCodes('&', name + " " + target.getMessage());
    }

    public String getMessage(Msg format, Object... args) {
        Msg target = format;
        for (Msg msg : this.messages) {
            if (msg.getName().equalsIgnoreCase(format.getName())) {
                target = msg;
            }
        }
        String newMsg = String.format(target.getMessage(), args);
        return ChatColor.translateAlternateColorCodes('&', name + " " + newMsg);
    }

    public void msg(CommandSender sender, Msg message) {
        sender.sendMessage(this.getMessage(message));
    }

    public void msg(CommandSender sender, Msg message, Object... args) {
        sender.sendMessage(this.getMessage(message, args));
    }

    public void broadcast(Msg message) {
        Bukkit.getServer().broadcastMessage(this.getMessage(message));
    }

    public void broadcast(Msg message, Object... args) {
        Bukkit.getServer().broadcastMessage(this.getMessage(message, args));
    }
}