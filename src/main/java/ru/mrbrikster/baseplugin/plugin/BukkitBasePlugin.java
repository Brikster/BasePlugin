package ru.mrbrikster.baseplugin.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbrikster.baseplugin.commands.BukkitCommand;
import ru.mrbrikster.baseplugin.config.BukkitConfiguration;
import ru.mrbrikster.baseplugin.config.Configuration;
import ru.mrbrikster.baseplugin.scheduler.BukkitScheduler;
import ru.mrbrikster.baseplugin.scheduler.Scheduler;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;

public abstract class BukkitBasePlugin extends JavaPlugin implements BasePlugin {

    public Set<BukkitCommand> commands = new HashSet<>();
    private Configuration mainConfiguration;
    private IdentityHashMap<String, Configuration> configurations
            = new IdentityHashMap<>();

    @Override
    public void onDisable() {
        commands.forEach(command -> command.unregister(this));
        commands.clear();

        super.onDisable();
    }

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

    @Override
    public Scheduler getScheduler() {
        return new BukkitScheduler(this);
    }

}
