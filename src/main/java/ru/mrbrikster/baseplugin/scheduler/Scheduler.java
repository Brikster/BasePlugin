package ru.mrbrikster.baseplugin.scheduler;

import java.util.concurrent.TimeUnit;

public interface Scheduler {

    Task schedule(Runnable runnable, long delay, TimeUnit timeUnit);

    Task schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit);

    Task schedule(Runnable runnable, long delay, TimeUnit timeUnit, boolean async);

    Task schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit, boolean async);

}
