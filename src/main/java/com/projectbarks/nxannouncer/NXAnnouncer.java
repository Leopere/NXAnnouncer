package com.projectbarks.nxannouncer;

import com.projectbarks.nxannouncer.announcer.Timer;
import com.projectbarks.nxannouncer.commands.Commands;
import com.projectbarks.nxannouncer.config.Config;
import com.projectbarks.nxannouncer.config.MessageManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NXAnnouncer extends JavaPlugin implements Listener {

    private static final Logger LOG = Logger.getLogger(NXAnnouncer.class.getName());
    private static Font font;

    /**
     * @return the LOG
     */
    public static Logger getLOG() {
        return LOG;
    }

    public static Font getFont() {
        return font;
    }

    public static void setFont(Font font) throws Exception {
        if (NXAnnouncer.font != null) {
            throw new Exception("Font has alrady been initilized");
        }
        NXAnnouncer.font = font;
    }
    private Config conf;
    private Timer timer;
    private MessageManager mm;
    private Commands commands;
    private FinishEnable finishEnable;

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
            setFont(new Font(this.getResource("font.bin")));
        } catch (Exception ex) {
            Logger.getLogger(NXAnnouncer.class.getName()).log(Level.SEVERE, null, ex);
        }
        conf.setup();
        mm.load();
        mm.save();
        //We create the font loading thregad
        finishEnable = new FinishEnable(this);
        finishEnable.runTaskTimer(this, 20L, 20L);
        font.runTaskLaterAsynchronously(this, 0L);

        if (!conf.isNoPlayers()) {
            getLOG().log(Level.INFO, "Disabling sending messages with 0 players online!");
        }

        this.getServer().getPluginCommand("nxannouncer").setExecutor(commands);
    }

    @Override
    public void onDisable() {
        try {
            timer.cancel();
            this.conf.getAnnouncements().clear();
            finishEnable.cancel();
        } catch (Exception ex) {
        }
        font.cancel();
    }

    public Config getConf() {
        return conf;
    }

    public Timer getTimer() {
        return timer;
    }

    public MessageManager getMm() {
        return mm;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
