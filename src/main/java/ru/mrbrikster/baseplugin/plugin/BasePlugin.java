package ru.mrbrikster.baseplugin.plugin;

import ru.mrbrikster.baseplugin.config.Configuration;

public interface BasePlugin {

    public Configuration getConfiguration();

    public Configuration getConfiguration(String fileName);

}
