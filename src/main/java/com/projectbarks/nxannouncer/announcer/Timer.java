package com.projectbarks.nxannouncer.announcer;

import com.projectbarks.nxannouncer.NXAnnouncer;
import com.projectbarks.nxannouncer.config.Config;
import java.util.logging.Level;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable implements Runnable {

    private int count;
    private NXAnnouncer main;
    private Config config;

    public Timer(NXAnnouncer main) {
        this.count = 0;
        this.main = main;
        this.config = main.getConf();
    }

    @Override
    public void run() {
        if (config.getAnnouncements().size() > 0) {
            if (main.getServer().getOnlinePlayers().length <= 0 && config.isNoPlayers()) {
                NXAnnouncer.getLOG().log(Level.INFO, "Not enough players broadcast cancled");
                return;
            }
            Announcement announcement = config.getAnnouncements().get(getCount());
            this.broadcast(config.getColor() + announcement.getColorizedHeader());
            for (String message : announcement.getTranslatedMessage()) {
                String colorizedMessage = Announcement.colorize(message);
                String dynamicChar = Announcement.formatChar(colorizedMessage,
                                                             announcement.getColorizedHeader(),
                                                             announcement.getColorizedFooter());
                this.broadcast(config.getColor() + dynamicChar + colorizedMessage);
            }
            this.broadcast(config.getColor() + announcement.getColorizedFooter());
            setCount(count + 1);
            if (getCount() >= config.getAnnouncements().size()) {
                setCount(0);
            }
            NXAnnouncer.getLOG().log(Level.INFO, "{0}Message {1} has been sent to {2} players.", new Object[]{config.getPluginName(), getCount(), main.getServer().getOnlinePlayers().length});
        }
    }

    private void broadcast(String message) {
        main.getServer().broadcastMessage(message);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
