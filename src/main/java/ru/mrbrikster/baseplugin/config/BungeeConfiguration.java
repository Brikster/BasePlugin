package ru.mrbrikster.baseplugin.config;

import ru.mrbrikster.baseplugin.plugin.BungeeBasePlugin;

import java.io.File;
import java.io.IOException;

public class BungeeConfiguration extends Configuration {

    private final File file;
    private final BungeeBasePlugin bungeeBasePlugin;
    private net.md_5.bungee.config.Configuration configuration;

    public BungeeConfiguration(BungeeBasePlugin bungeeBasePlugin) {
        this("config.yml", bungeeBasePlugin);
    }

    public BungeeConfiguration(String fileName, BungeeBasePlugin bungeeBasePlugin) {
        this.bungeeBasePlugin = bungeeBasePlugin;
        //bungeeBasePlugin.saveDefaultConfig();

        this.file = new File(bungeeBasePlugin.getDataFolder(), fileName);
        try {
            this.configuration = net.md_5.bungee.config.ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
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
