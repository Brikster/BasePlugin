package ru.mrbrikster.baseplugin.scheduler;

import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;

import java.util.concurrent.TimeUnit;

public class BukkitScheduler implements Scheduler {

    private final BukkitBasePlugin bukkitBasePlugin;

    public BukkitScheduler(BukkitBasePlugin bukkitBasePlugin) {
        this.bukkitBasePlugin = bukkitBasePlugin;
    }

    @Override
    public Task schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        return this.schedule(runnable, delay, timeUnit, false);
    }

    @Override
    public Task schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
        return this.schedule(runnable, delay, period, timeUnit, false);
    }

    @Override
    public Task schedule(Runnable runnable, long delay, TimeUnit timeUnit, boolean async) {
        if (async) {
            return new BukkitTask(bukkitBasePlugin.getServer().getScheduler().runTaskLaterAsynchronously(bukkitBasePlugin, runnable, timeUnit.toSeconds(delay) * 20));
        } else {
            return new BukkitTask(bukkitBasePlugin.getServer().getScheduler().runTaskLater(bukkitBasePlugin, runnable, timeUnit.toSeconds(delay) * 20));
        }
    }

    @Override
    public Task schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit, boolean async) {
        if (async) {
            return new BukkitTask(bukkitBasePlugin.getServer().getScheduler().runTaskTimerAsynchronously(bukkitBasePlugin, runnable, timeUnit.toSeconds(delay) * 20, timeUnit.toSeconds(period) * 20));
        } else {
            return new BukkitTask(bukkitBasePlugin.getServer().getScheduler().runTaskTimer(bukkitBasePlugin, runnable, timeUnit.toSeconds(delay) * 20, timeUnit.toSeconds(period) * 20));
        }
    }

}
