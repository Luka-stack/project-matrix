package com.lukastack.projectmatrix.threadpools;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExecutorCachedFutures extends ThreadPoolExecutor {

    private List<Future<?>> futures = new LinkedList<>();

    public ThreadPoolExecutorCachedFutures(int corePoolSize, int maximumPoolSize,
                                           long keepAliveTime, TimeUnit unit,
                                           BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public Future<?> submit(Runnable task) {
        Future<?> futureTask = super.submit(task);
        futures.add(futureTask);

        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Future<T> futureTask = super.submit(task);
        futures.add(futureTask);

        return futureTask;
    }

    public List<Future<?>> getFutures() {
        return futures;
    }

    public void clearFuturesCache() {
        futures = new LinkedList<>();
    }
}
