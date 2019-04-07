package ru.mrbrikster.baseplugin.plugin;

import ru.mrbrikster.baseplugin.config.Configuration;
import ru.mrbrikster.baseplugin.scheduler.Scheduler;

public interface BasePlugin {

    public Configuration getConfiguration();

    public Configuration getConfiguration(String fileName);

    public Scheduler getScheduler();

}
