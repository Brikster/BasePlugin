package ru.mrbrikster.baseplugin.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbrikster.baseplugin.config.BukkitConfiguration;
import ru.mrbrikster.baseplugin.config.Configuration;

import java.util.IdentityHashMap;

public abstract class BukkitBasePlugin extends JavaPlugin implements BasePlugin {

    private Configuration mainConfiguration;
    private IdentityHashMap<String, Configuration> configurations
            = new IdentityHashMap<>();

    @Override
    public Configuration getConfiguration() {
        if (mainConfiguration == null) {
            mainConfiguration = new BukkitConfiguration(this);
        }

        return mainConfiguration;
    }

    @Override
    public Configuration getConfiguration(String fileName) {
        if (!configurations.containsKey(fileName.toLowerCase())) {
            configurations.put(fileName.toLowerCase(), new BukkitConfiguration(fileName, this));
        }

        return configurations.get(fileName.toLowerCase());
    }

}
