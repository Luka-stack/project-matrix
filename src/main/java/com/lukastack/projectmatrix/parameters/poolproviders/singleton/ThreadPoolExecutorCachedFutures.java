package com.lukastack.projectmatrix.parameters.poolproviders.singleton;

import java.util.concurrent.*;

public class ThreadPoolExecutorCachedFutures extends ThreadPoolExecutor {

    private static final int TASK_CAPACITY = 1000000;
    private CapacityQueue<Future<?>> futures;

    public ThreadPoolExecutorCachedFutures(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                           TimeUnit unit, BlockingQueue<Runnable> workQueue) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                new ThreadPoolExecutor.CallerRunsPolicy());

        futures = new CapacityQueue<>(TASK_CAPACITY);
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

    public CapacityQueue<Future<?>> getFutures() {
        return futures;
    }

    public void clearFuturesCache() {
        futures.clear();
    }
}
