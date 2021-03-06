package ru.mrbrikster.baseplugin.config;

import net.md_5.bungee.config.YamlConfiguration;
import org.apache.commons.io.FileUtils;
import ru.mrbrikster.baseplugin.plugin.BungeeBasePlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BungeeConfiguration extends Configuration {

    private File file;
    private net.md_5.bungee.config.Configuration configuration;

    public BungeeConfiguration(BungeeBasePlugin bungeeBasePlugin) {
        this(bungeeBasePlugin, "config.yml");
    }

    public BungeeConfiguration(BungeeBasePlugin bungeeBasePlugin, String fileName) {
        try {
            BungeeConfiguration.saveDefaultConfig(bungeeBasePlugin, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.file = new File(bungeeBasePlugin.getDataFolder(), fileName);
        try {
            this.configuration = net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public BungeeConfiguration(net.md_5.bungee.config.Configuration configuration) {
        this.configuration = configuration;
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
        if (file == null) {
            throw new IllegalArgumentException("BungeeConfiguration is loaded not from file");
        }

        try {
            net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        if (file == null) {
            throw new IllegalArgumentException("BungeeConfiguration is loaded not from file");
        }

        try {
            this.configuration = net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.reload();
    }

    private static void saveDefaultConfig(BungeeBasePlugin bungeeBasePlugin, String fileName) throws IOException {
        File dataFolder = bungeeBasePlugin.getDataFolder();

        if (!dataFolder.exists())
            dataFolder.mkdir();

        File configFile = new File(dataFolder, fileName);

        if (!configFile.exists()) {
            InputStream stream = bungeeBasePlugin.getClass().getResourceAsStream("/" + fileName);
            FileUtils.copyInputStreamToFile(stream, new File(dataFolder, "/" + fileName));
        }
    }

}
