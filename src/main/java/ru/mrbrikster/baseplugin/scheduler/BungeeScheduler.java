package ru.mrbrikster.baseplugin.scheduler;

import ru.mrbrikster.baseplugin.plugin.BungeeBasePlugin;

import java.util.concurrent.TimeUnit;

public class BungeeScheduler implements Scheduler {

    private final BungeeBasePlugin bungeeBasePlugin;

    public BungeeScheduler(BungeeBasePlugin bungeeBasePlugin) {
        this.bungeeBasePlugin = bungeeBasePlugin;
    }

    @Override
    public Task schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        return this.schedule(runnable, delay, timeUnit, true);
    }

    @Override
    public Task schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
        return this.schedule(runnable, delay, period, timeUnit, true);
    }

    @Override
    public Task schedule(Runnable runnable, long delay, TimeUnit timeUnit, boolean async) {
        if (!async)
            throw new UnsupportedOperationException("BungeeCord doesn't support synchronous tasks");

        return new BungeeTask(bungeeBasePlugin.getProxy().getScheduler().schedule(bungeeBasePlugin, runnable, delay, timeUnit));
    }

    @Override
    public Task schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit, boolean async) {
        if (!async)
            throw new UnsupportedOperationException("BungeeCord doesn't support synchronous tasks");

        return new BungeeTask(bungeeBasePlugin.getProxy().getScheduler().schedule(bungeeBasePlugin, runnable, delay, period, timeUnit));
    }

}
