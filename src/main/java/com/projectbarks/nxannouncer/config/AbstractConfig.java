package com.projectbarks.nxannouncer.config;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Brandon Barker
 */
public abstract class AbstractConfig {

    //Fields//
    //-----Fields Final-----//
    protected final YamlConfiguration config;
    //-----Fields Default-----//
    protected File file;

    /**
     *
     * @param plugin
     * @param filename
     */
    public AbstractConfig(Plugin plugin, String filename) {
        this(new File(plugin.getDataFolder(), filename + ".yml"));
    }

    /**
     *
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

    //-----Protected Final Mutators-----//
    /**
     * Returns the YALM configuration.
     *
     * @return the config
     */
    protected YamlConfiguration getConfig() {
        return config;
    }

    /**
     * Gets the file designated to the config. This can be defaulted to null if
     * not created properly.
     *
     * @return the file
     */
    protected File getFile() {
        return file;
    }

    /**
     * Sets the file designated to the config.
     *
     * @param file the file to set
     */
    protected void setFile(File file) {
        this.file = file;
    }
}
