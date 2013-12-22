package com.projectbarks.nxannouncer;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Brandon Barker
 */
public class AnnouncerFinalizer extends BukkitRunnable {

    private final NXAnnouncer nxa;

    public AnnouncerFinalizer(NXAnnouncer nxa) {
        this.nxa = nxa;
    }

    public void run() {
        nxa.getConf().load();
        long interval = (long) (20L * nxa.getConf().getInterval());
        nxa.getTimer().runTaskTimer(nxa, interval, interval);
        this.cancel();
    }
}
