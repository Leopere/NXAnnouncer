package com.projectbarks.nxannouncer;

import com.projectbarks.nxannouncer.announcer.Timer;
import com.projectbarks.nxannouncer.commands.Commands;
import com.projectbarks.nxannouncer.config.Config;
import com.projectbarks.nxannouncer.config.MessageManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

@Log
public class NXAnnouncer extends JavaPlugin implements Listener {

    private static FontManager font;

    public static FontManager getFont() {
        return font;
    }

    public static void setFont(FontManager font) throws Exception {
        if (NXAnnouncer.font != null) {
            throw new Exception("Font was already  initilized");
        }
        NXAnnouncer.font = font;
    }

    @Getter
    private Config conf;
    @Getter
    @Setter
    private Timer timer;
    @Getter
    private MessageManager mm;
    @Getter
    private Commands commands;

    @Override
    public void onLoad() {
        this.conf = new Config(this);
        this.timer = new Timer(this);
        this.mm = new MessageManager(this);
        this.commands = new Commands(this);
    }

    @Override
    public void onEnable() {
        try {
            setFont(new FontManager(this.getResource("font.bin"), this));
        } catch (Exception ex) {
            Logger.getLogger(NXAnnouncer.class.getName()).log(Level.SEVERE, null, ex);
        }
        conf.setup();
        mm.load();
        mm.save();
        //We create the font loading thread
        font.runTaskLaterAsynchronously(this, 0L);

        if (!conf.isNoPlayers()) {
            log.log(Level.INFO, "Disabling sending messages with 0 players online!");
        }

        this.getServer().getPluginCommand("nxannouncer").setExecutor(commands);
    }

    @Override
    public void onDisable() {
        try {
            timer.cancel();
            font.cancel();
            font.getFinishEnable().cancel();
            this.conf.getAnnouncements().clear();
        } catch (Exception ex) {
            NXAnnouncer.log.log(Level.WARNING, "Disabled failed");
        }
    }
}
