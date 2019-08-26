package ru.mrbrikster.baseplugin.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Configuration {

    private List<Consumer<Configuration>> consumerList = new ArrayList<>();

    public abstract ConfigurationNode getNode(String path);

    public abstract void save();

    public void reload() {
        consumerList.forEach(consumer -> consumer.accept(this));
    }

    public void onReload(Consumer<Configuration> consumer) {
        this.consumerList.add(consumer);
    }

    public interface ReloadHandler {

        void onConfigurationReload();

    }

}
