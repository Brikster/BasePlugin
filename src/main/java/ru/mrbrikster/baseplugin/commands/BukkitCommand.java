package ru.mrbrikster.baseplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import ru.mrbrikster.baseplugin.plugin.BasePlugin;
import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public abstract class BukkitCommand extends Command implements BaseCommand {

    private static SimpleCommandMap commandMap;

    static {
        SimplePluginManager simplePluginManager = (SimplePluginManager) Bukkit.getServer()
                .getPluginManager();

        Field commandMapField = null;
        try {
            commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(commandMapField).setAccessible(true);

        try {
            BukkitCommand.commandMap = (SimpleCommandMap) commandMapField.get(simplePluginManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public BukkitCommand(String name, String... aliases) {
        super(name);
        setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        this.handle(sender, label, args);
        return true;
    }

    public abstract void handle(CommandSender sender, String label, String[] args);

    @Override
    public void register(BasePlugin basePlugin) {
        if (!(basePlugin instanceof BukkitBasePlugin))
            throw new UnsupportedOperationException();

        register(commandMap);
        commandMap.register(((BukkitBasePlugin) basePlugin).getName().toLowerCase(), this);
    }

    @Override
    public void unregister(BasePlugin basePlugin) {
        if (!(basePlugin instanceof BukkitBasePlugin))
            throw new UnsupportedOperationException();

        unregister(commandMap);

        try {
            Field field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);

            @SuppressWarnings("all")
            Map<String, Command> commands = (Map<String, Command>) field.get(commandMap);
            commands.remove(getLabel());
            commands.remove(((BukkitBasePlugin) basePlugin).getName().toLowerCase() + ":" + getLabel());

            getAliases().forEach(alias -> {
                commands.remove(alias);
                commands.remove(((BukkitBasePlugin) basePlugin).getName().toLowerCase() + ":" + alias);
            });

            field.set(commandMap, commands);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
