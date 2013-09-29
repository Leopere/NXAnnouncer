package com.projectbarks.nxannouncer;

import com.projectbarks.nxannouncer.config.Config;
import com.projectbarks.nxannouncer.announcer.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BarksSimpleAnnouncer extends JavaPlugin implements Listener {

    private static final Logger LOG = Logger.getLogger(BarksSimpleAnnouncer.class.getName());

    /**
     * @return the LOG
     */
    public static Logger getLOG() {
        return LOG;
    }
    private Font font;
    private Config conf;
    private Timer timer;

    @Override
    public void onLoad() {
        this.conf = new Config(this);
        this.timer = new Timer(this);
    }

    @Override
    public void onEnable() {
        conf.setup();
        conf.load();
        Font.load(this);

        if (!conf.isNoPlayers()) {
            getLOG().log(Level.INFO, "Disabling sending messages with 0 players online!");
        }

        long interval = (long) (20L * conf.getInterval());
        timer.runTaskTimer(this, interval, interval);
    }

    @Override
    public void onDisable() {
        try {
            timer.cancel();
            this.conf.getAnnouncements().clear();
        } catch (Exception ex) {
        }
    }

    /**
     * @return the conf
     */
    public Config getConf() {
        return conf;
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }
}
