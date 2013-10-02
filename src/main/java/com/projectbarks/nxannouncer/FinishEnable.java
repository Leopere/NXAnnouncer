package com.projectbarks.nxannouncer;

import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Brandon Barker
 */
public class FinishEnable extends BukkitRunnable {

    private NXAnnouncer nxa;

    public FinishEnable(NXAnnouncer nxa) {
        this.nxa = nxa;
    }

    public void run() {
        if (NXAnnouncer.getFont().isLoaded()) {
            nxa.getConf().load();
            long interval = (long) (20L * nxa.getConf().getInterval());
            nxa.getTimer().runTaskTimer(nxa, interval, interval);
            this.cancel();
        }
    }
}
