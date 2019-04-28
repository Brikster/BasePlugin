package ru.mrbrikster.baseplugin.config;

import net.md_5.bungee.config.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BungeeConfigurationNodeImpl implements ConfigurationNode {

    private final Configuration configuration;
    private final String path;

    BungeeConfigurationNodeImpl(Configuration configuration, String path) {
        this.configuration = configuration;
        this.path = path;
    }

    @Override
    public String getName() {
        String[] path = this.path.split("\\.");

        return path[path.length - 1];
    }

    @Override
    public Object get(Object def) {
        return configuration.get(path, def);
    }

    @Override
    public boolean getAsBoolean(boolean def) {
        return configuration.getBoolean(path, def);
    }

    @Override
    public String getAsString(String def) {
        return configuration.getString(path, def);
    }

    @Override
    public long getAsLong(long def) {
        return configuration.getLong(path, def);
    }

    @Override
    public int getAsInt(int def) {
        return configuration.getInt(path, def);
    }

    @Override
    public <T> List<T> getAsList(List<T> def) {
        return (List<T>) configuration.getList(path, def);
    }

    @Override
    public List<Map<?, ?>> getAsMapList() {
        throw new UnsupportedOperationException("BungeeCord configuration doesn't support this method.");
    }

    @Override
    public List<String> getAsStringList() {
        return configuration.getStringList(path);
    }

    public Configuration getAsConfigurationSection() {
        return configuration.getSection(path);
    }

    @Override
    public ConfigurationNode getNode(String path) {
        if (configuration.contains(this.path + "." + path)
                || configuration.getSection(this.path + "." + path) != null)
            return new BungeeConfigurationNodeImpl(configuration, this.path + "." + path);

        return new EmptyConfigurationNode(path);
    }

    @Override
    public List<ConfigurationNode> getChildNodes() {
        Configuration section = getAsConfigurationSection();

        if (section == null) {
            return Collections.emptyList();
        }

        return section.getKeys().stream()
                .map(key -> new BungeeConfigurationNodeImpl(configuration, path + "." + key)).collect(Collectors.toList());
    }

    @Override
    public void set(Object value) {
        configuration.set(path, value);
    }

    @Override
    public boolean isEmpty() {
        return !configuration.contains(path);
    }

}
