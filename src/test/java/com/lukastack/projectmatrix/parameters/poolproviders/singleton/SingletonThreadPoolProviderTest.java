package com.lukastack.projectmatrix.parameters.poolproviders.singleton;

import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class SingletonThreadPoolProviderTest {

    private SingletonThreadPoolProvider threadPoolProvider;
    private int coreSize;
    private int maxSize;
    private long keepAlive;
    private TimeUnit timeUnit;

    @BeforeEach
    void setUp() {
        this.coreSize = 0;
        this.maxSize = 10;
        this.keepAlive = 10L;
        this.timeUnit = TimeUnit.MILLISECONDS;

        threadPoolProvider = new SingletonThreadPoolProvider(coreSize, maxSize, keepAlive, timeUnit);
    }

    @Test
    void constructor_createsProvider() {

        var provider = new SingletonThreadPoolProvider(coreSize, maxSize, keepAlive, timeUnit);

        Assertions.assertEquals(coreSize, provider.getCorePoolSize());
        Assertions.assertEquals(maxSize, provider.getMaximumPoolSize());
        Assertions.assertEquals(keepAlive, provider.getKeepAlive());
        Assertions.assertEquals(timeUnit, provider.getTimeUnit());
    }

    @Test
    void provideThreadPool_returnInstanceOfThreadPoolExecutor() {

        Assertions.assertNotNull(threadPoolProvider.provideThreadPool());
    }

    @Test
    void provideThreadPool_returnSingleton() {

        var threadPoolFirst = threadPoolProvider.provideThreadPool();
        var threadPoolSecond = threadPoolProvider.provideThreadPool();

        Assertions.assertEquals(threadPoolFirst, threadPoolSecond);
    }

    @Test
    void close_providerCloseThread() throws InterruptedException {

        var threadPool = threadPoolProvider.provideThreadPool();

        Assertions.assertFalse(threadPool.isShutdown());

        threadPoolProvider.close();

        Assertions.assertTrue(threadPool.isShutdown());
    }

    @Test
    void waitForCompletion_clearsFuturesCacheAfterExecution() throws ExecutionException, InterruptedException {

        var threadPool = threadPoolProvider.provideThreadPool();

        threadPool.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        threadPoolProvider.waitForCompletion();

        Assertions.assertEquals(0, threadPool.getFutures().size());
    }

    @Test
    void waitForCompletion_waitTillTaskCompletion() throws ExecutionException, InterruptedException {

        var threadPool = threadPoolProvider.provideThreadPool();

        threadPool.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        threadPool.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        long startTime = System.nanoTime();
        threadPoolProvider.waitForCompletion();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;

        Assertions.assertTrue(duration > 2000);

        threadPoolProvider.close();
    }
}