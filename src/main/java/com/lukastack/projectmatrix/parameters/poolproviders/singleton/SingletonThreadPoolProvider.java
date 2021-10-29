package com.lukastack.projectmatrix.parameters.poolproviders.singleton;

import com.lukastack.projectmatrix.parameters.poolproviders.ThreadPoolProvider;

import java.util.concurrent.*;

public class SingletonThreadPoolProvider extends ThreadPoolProvider {

    private ThreadPoolExecutorCachedFutures threadPool = null;

    public SingletonThreadPoolProvider() {

        this.corePoolSize = Runtime.getRuntime().availableProcessors();
        this.maximumPoolSize = Runtime.getRuntime().availableProcessors();
        this.keepAlive = 0L;
        this.keepAliveUnit = TimeUnit.MILLISECONDS;
    }

    public SingletonThreadPoolProvider(int corePoolSize, int maximumPoolSize, long keepAlive, TimeUnit keepAliveUnit) {

        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAlive = keepAlive;
        this.keepAliveUnit = keepAliveUnit;
    }

    @Override
    public ThreadPoolExecutorCachedFutures provideThreadPool() {

        if (this.threadPool == null) {

            this.threadPool = new ThreadPoolExecutorCachedFutures(this.corePoolSize, this.maximumPoolSize,
                    this.keepAlive, this.keepAliveUnit, newWorkQueueInstance());
        }

        return this.threadPool;
    }

    @Override
    public void close() throws InterruptedException {

        this.threadPool.shutdown();
        if (!this.threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
            this.threadPool.shutdownNow();

            while (!this.threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                Thread.sleep(60000);
            }
        }
        this.threadPool = null;
    }

    public void close(int timeout, TimeUnit unit) throws InterruptedException {

        this.threadPool.shutdown();
        if (!this.threadPool.awaitTermination(timeout, unit)) {
            this.threadPool.shutdownNow();

            while (!this.threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                Thread.sleep(60000);
            }
        }
        this.threadPool = null;
    }

    public void waitForCompletion() throws InterruptedException, ExecutionException {

        var futures = this.threadPool.getFutures();

        for (var future : futures) {
            future.get();
        }

        this.threadPool.clearFuturesCache();
    }
}
