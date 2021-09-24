package com.lukastack.projectmatrix.parameters.threads;

import com.lukastack.projectmatrix.threadpools.ThreadPoolExecutorCachedFutures;

import javax.management.InvalidAttributeValueException;
import java.util.concurrent.*;

public class SingletonThreadPoolProvider<E extends BlockingQueue<Runnable>> extends AbstractThreadPoolProvider {

    private ThreadPoolExecutorCachedFutures threadPool = null;

    public SingletonThreadPoolProvider() {

        this.corePoolSize = 3;
        this.maximumPoolSize = 3;
        this.keepAlive = 0L;
        this.keepAliveUnit = TimeUnit.SECONDS;
        this.threadPoolType = ThreadPoolType.FIXED;
    }

    public SingletonThreadPoolProvider(int corePoolSize, int maximumPoolSize, long keepAlive,
                                TimeUnit keepAliveUnit, ThreadPoolType threadPoolType) throws InvalidAttributeValueException {

        if (threadPoolType == ThreadPoolType.CUSTOM) {
            throw new InvalidAttributeValueException("Cannot create provider with Custom queue without queue");
        }

        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAlive = keepAlive;
        this.keepAliveUnit = keepAliveUnit;
        this.threadPoolType = threadPoolType;
    }

    public SingletonThreadPoolProvider(int corePoolSize, int maximumPoolSize, long keepAlive,
                                       TimeUnit keepAliveUnit, Class<E> workQueueClass) {

        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAlive = keepAlive;
        this.keepAliveUnit = keepAliveUnit;
        this.threadPoolType = ThreadPoolType.CUSTOM;
        this.workQueueClass = workQueueClass;
    }

    @Override
    public ThreadPoolExecutorCachedFutures provideThreadPool() {

        if (this.threadPool == null) {

            this.threadPool = new ThreadPoolExecutorCachedFutures(this.corePoolSize, this.maximumPoolSize,
                    this.keepAlive, this.keepAliveUnit,
                    newWorkQueueInstance());
        }

        return this.threadPool;
    }

    // TODO Extract close to Interface ? Abstract class ?
    // TODO Should throw error ? Log error ? if couldn't close thread pool
    // TODO Should return bool ?
    public void close() throws InterruptedException {

        this.threadPool.shutdown();
        if (!this.threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
            this.threadPool.shutdownNow();

            if (!this.threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                // TODO throw an custom error
            }
        }
        this.threadPool = null;
    }

    public void close(int timeout, TimeUnit unit) throws InterruptedException {

        this.threadPool.shutdown();
        if (!this.threadPool.awaitTermination(timeout, unit)) {
            this.threadPool.shutdownNow();

            if (!this.threadPool.awaitTermination(timeout, unit)) {
                // TODO throw an custom error
            }
        }
        this.threadPool = null;
    }

    // TODO Throw exception from these function ?
    public void waitForCompletion() throws InterruptedException, ExecutionException {

        var futures = this.threadPool.getFutures();

        for (var future : futures) {
            future.get();
        }

        this.threadPool.clearFuturesCache();
    }
}
