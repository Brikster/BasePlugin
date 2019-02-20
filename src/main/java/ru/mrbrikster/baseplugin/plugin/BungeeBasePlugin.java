package ru.mrbrikster.baseplugin.plugin;

import net.md_5.bungee.api.plugin.Plugin;
import ru.mrbrikster.baseplugin.config.BungeeConfiguration;
import ru.mrbrikster.baseplugin.config.Configuration;

import java.util.IdentityHashMap;

public class BungeeBasePlugin extends Plugin implements BasePlugin {

    private Configuration mainConfiguration;
    private IdentityHashMap<String, Configuration> configurations
            = new IdentityHashMap<>();

    @Override
    public Configuration getConfiguration() {
        if (mainConfiguration == null) {
            mainConfiguration = new BungeeConfiguration(this);
        }

        return mainConfiguration;
    }

    @Override
    public Configuration getConfiguration(String fileName) {
        if (!configurations.containsKey(fileName.toLowerCase())) {
            configurations.put(fileName.toLowerCase(), new BungeeConfiguration(this, fileName));
        }

        return configurations.get(fileName.toLowerCase());
    }

}
