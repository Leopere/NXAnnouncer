package com.projectbarks.nxannouncer.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Brandon Barker
 */
public abstract class AbstractConfig {

    //Fields//
    //-----Fields Final-----//

    /**
     * Returns the YALM configuration.
     *
     * @return the config
     */
    @Getter(AccessLevel.PROTECTED)
    protected YamlConfiguration config;
    //-----Fields Default-----//
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected File file;

    /**
     * @param plugin
     * @param filename
     */
    public AbstractConfig(Plugin plugin, String filename) {
        this(new File(plugin.getDataFolder(), filename + ".yml"));
    }

    /**
     * @param file
     */
    public AbstractConfig(File file) {
        this.config = new YamlConfiguration();
        this.file = file;
    }

    public void load() {
        try {
            fileCheck();
            this.config.load(file);
            this.loadConfig();
        } catch (Exception ex) {
            Logger.getLogger(AbstractConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        this.saveConfig();
        try {
            this.getConfig().save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.config = new YamlConfiguration();
        this.load();
        this.save();
    }

    /**
     *
     */
    abstract protected void loadConfig();

    /**
     *
     */
    abstract protected void saveConfig();

    //-----Protected Mutators-----//

    /**
     * Internal method, creates the file if it doesn't exist
     *
     * @throws Exception
     */
    protected void fileCheck() throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

}
