package ru.mrbrikster.baseplugin.config;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BukkitConfiguration extends Configuration {

    private final File file;
    private YamlConfiguration configuration;

    public BukkitConfiguration(BukkitBasePlugin bukkitBasePlugin) {
        this("config.yml", bukkitBasePlugin);
    }

    public BukkitConfiguration(String fileName, BukkitBasePlugin bukkitBasePlugin) {
        try {
            BukkitConfiguration.saveDefaultConfig(bukkitBasePlugin, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private static void saveDefaultConfig(BukkitBasePlugin bukkitBasePlugin, String fileName) throws IOException {
        File dataFolder = bukkitBasePlugin.getDataFolder();

        if (!dataFolder.exists())
            dataFolder.mkdir();

        File configFile = new File(dataFolder, fileName);

        if (!configFile.exists()) {
            InputStream stream = bukkitBasePlugin.getClass().getResourceAsStream("/" + fileName);
            FileUtils.copyInputStreamToFile(stream, new File(dataFolder, "/" + fileName));
        }
    }

}
