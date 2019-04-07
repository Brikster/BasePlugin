package ru.mrbrikster.baseplugin.config;

import org.apache.commons.io.FileUtils;
import ru.mrbrikster.baseplugin.plugin.BasePlugin;
import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;
import ru.mrbrikster.baseplugin.plugin.BungeeBasePlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Configuration {

    private List<ReloadHandler> reloadHandlers = new ArrayList<>();

    public abstract ConfigurationNode getNode(String path);

    public abstract void save();

    public abstract void onReload();

    public void reload() {
        onReload();

        reloadHandlers.forEach(ReloadHandler::onConfigurationReload);
    }

    public void registerReloadHandler(ReloadHandler reloadHandler) {
        this.reloadHandlers.add(reloadHandler);
    }

    public interface ReloadHandler {

        void onConfigurationReload();

    }

}
