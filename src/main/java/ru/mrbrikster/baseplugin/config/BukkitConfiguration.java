package ru.mrbrikster.baseplugin.config;

import org.bukkit.configuration.file.YamlConfiguration;
import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;

import java.io.File;
import java.io.IOException;

public class BukkitConfiguration extends Configuration {

    private final File file;
    private YamlConfiguration configuration;

    public BukkitConfiguration(BukkitBasePlugin bukkitBasePlugin) {
        this("config.yml", bukkitBasePlugin);
    }

    public BukkitConfiguration(String fileName, BukkitBasePlugin bukkitBasePlugin) {
        bukkitBasePlugin.saveDefaultConfig();

        this.file = new File(bukkitBasePlugin.getDataFolder(), fileName);
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public ConfigurationNode getNode(String path) {
        if (configuration.contains(path)
                || configuration.getConfigurationSection(path) != null)
            return new BukkitConfigurationNodeImpl(configuration, path);

        return new EmptyConfigurationNode(path);
    }

    @Override
    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReload() {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

}
