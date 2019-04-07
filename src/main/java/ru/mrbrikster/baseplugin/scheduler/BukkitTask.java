package ru.mrbrikster.baseplugin.scheduler;

public class BukkitTask implements Task {

    private final org.bukkit.scheduler.BukkitTask bukkitTask;

    BukkitTask(org.bukkit.scheduler.BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    @Override
    public int getId() {
        return this.bukkitTask.getTaskId();
    }

    @Override
    public void cancel() {
        this.bukkitTask.cancel();
    }

}
