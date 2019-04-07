package ru.mrbrikster.baseplugin.scheduler;

import net.md_5.bungee.api.scheduler.ScheduledTask;

public class BungeeTask implements Task {

    private final ScheduledTask scheduledTask;

    BungeeTask(ScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    @Override
    public int getId() {
        return this.scheduledTask.getId();
    }

    @Override
    public void cancel() {
        this.scheduledTask.cancel();
    }

}
