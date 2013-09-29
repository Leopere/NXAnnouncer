package com.projectbarks.nxannouncer.announcer;

import com.projectbarks.nxannouncer.BarksSimpleAnnouncer;
import com.projectbarks.nxannouncer.config.Config;
import java.util.logging.Level;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable implements Runnable {

    private int count;
    private BarksSimpleAnnouncer main;
    private Config config;

    public Timer(BarksSimpleAnnouncer main) {
        this.count = 0;
        this.main = main;
        this.config = main.getConf();
    }

    @Override
    public void run() {
        if (main.getServer().getOnlinePlayers().length <= 0 && config.isNoPlayers()) {
            BarksSimpleAnnouncer.getLOG().log(Level.INFO, "Not enough players broadcast cancled");
            return;
        }
        Announcement announcement = config.getAnnouncements().get(count);
        this.broadcast(config.getColor() + announcement.getColorizedHeader());
        for (String message : announcement.getTranslatedMessage()) {
            String colorizedMessage = Announcement.colorize(message);
            String dynamicChar = Announcement.formatChar(colorizedMessage,
                                                         announcement.getColorizedHeader(),
                                                         announcement.getColorizedFooter());
            this.broadcast(config.getColor() + dynamicChar + colorizedMessage);
        }
        this.broadcast(config.getColor() + announcement.getColorizedFooter());
        count += 1;
        if (count + 1 > config.getAnnouncements().size()) {
            count = 0;
        }
        BarksSimpleAnnouncer.getLOG().log(Level.INFO, "{0}Message {1} has been sent to {2} players.", new Object[]{config.getPluginName(), count, main.getServer().getOnlinePlayers().length});
    }

    private void broadcast(String message) {
        main.getServer().broadcastMessage(message);
    }
}
