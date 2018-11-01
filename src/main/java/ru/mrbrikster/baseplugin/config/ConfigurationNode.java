package ru.mrbrikster.baseplugin.config;

import java.util.List;
import java.util.Map;

public interface ConfigurationNode {

    public String getName();

    public Object get(Object def);

    boolean getAsBoolean(boolean def);

    String getAsString(String def);

    long getAsLong(long def);

    int getAsInt(int def);

    List getAsList(List def);

    List<Map<?, ?>> getAsMapList();

    List<String> getAsStringList();

    ConfigurationNode getNode(String path);

    List<ConfigurationNode> getChildNodes();

    void set(Object value);

    boolean isEmpty();

}
