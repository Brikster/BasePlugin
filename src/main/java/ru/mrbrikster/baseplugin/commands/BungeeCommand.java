package ru.mrbrikster.baseplugin.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import ru.mrbrikster.baseplugin.plugin.BasePlugin;
import ru.mrbrikster.baseplugin.plugin.BungeeBasePlugin;

public abstract class BungeeCommand extends Command implements BaseCommand {

    public BungeeCommand(String name, String... aliases) {
        super(name, null, aliases);
    }

    @Override
    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public void register(BasePlugin basePlugin) {
        if (!(basePlugin instanceof BungeeBasePlugin))
            throw new UnsupportedOperationException();

        ((BungeeBasePlugin) basePlugin).getProxy().getPluginManager().registerCommand((Plugin) basePlugin, this);
    }

    @Override
    public void unregister(BasePlugin basePlugin) {
        if (!(basePlugin instanceof BungeeBasePlugin))
            throw new UnsupportedOperationException();

        ((BungeeBasePlugin) basePlugin).getProxy().getPluginManager().unregisterCommand(this);
    }

}
