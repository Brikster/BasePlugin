package ru.mrbrikster.baseplugin.commands;

import ru.mrbrikster.baseplugin.plugin.BasePlugin;

public interface BaseCommand {

    void register(BasePlugin basePlugin);

    void unregister(BasePlugin basePlugin);

}
