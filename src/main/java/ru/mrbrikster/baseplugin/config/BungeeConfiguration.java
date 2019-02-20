package ru.mrbrikster.baseplugin.config;

import org.apache.commons.io.FileUtils;
import ru.mrbrikster.baseplugin.plugin.BungeeBasePlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BungeeConfiguration extends Configuration {

    private final File file;
    private net.md_5.bungee.config.Configuration configuration;

    public BungeeConfiguration(BungeeBasePlugin bungeeBasePlugin) {
        this(bungeeBasePlugin, "config.yml");
    }

    public BungeeConfiguration(BungeeBasePlugin bungeeBasePlugin, String fileName) {
        saveDefaultConfig(bungeeBasePlugin, fileName);

        this.file = new File(bungeeBasePlugin.getDataFolder(), fileName);
        try {
            this.configuration = net.md_5.bungee.config.ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefaultConfig(BungeeBasePlugin bungeeBasePlugin, String fileName) {
        try {
            if (!bungeeBasePlugin.getDataFolder().exists())
                bungeeBasePlugin.getDataFolder().mkdir();

            File configFile = new File(bungeeBasePlugin.getDataFolder(), fileName);

            if (!configFile.exists()) {
                InputStream stream = bungeeBasePlugin.getClass().getResourceAsStream("/" + fileName);
                FileUtils.copyInputStreamToFile(stream, new File(bungeeBasePlugin.getDataFolder(), fileName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ConfigurationNode getNode(String path) {
        if (configuration.contains(path)
                || configuration.getSection(path) != null)
            return new BungeeConfigurationNodeImpl(configuration, path);

        return new EmptyConfigurationNode(path);
    }

    @Override
    public void save() {
        try {
            net.md_5.bungee.config.ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReload() {
        try {
            this.configuration = net.md_5.bungee.config.ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
