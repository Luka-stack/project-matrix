package com.lukastack.projectmatrix.threadpools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

class ThreadPoolExecutorCachedFuturesTest {

    private ThreadPoolExecutorCachedFutures threadPool;

    @BeforeEach
    void setUp() {
        threadPool = new ThreadPoolExecutorCachedFutures(3, 3, 0L,
                TimeUnit.SECONDS,  new LinkedBlockingDeque<>());
    }

    @Test
    void submit_CallableTask_AddsFutureToList(){

        List<Callable<String>> callables = Arrays.asList(
                new DelayedCallable("fast thread", 100),
                new DelayedCallable("slow thread", 3000));

        for (var call : callables) {
            this.threadPool.submit(call);
        }

        var futures = this.threadPool.getFutures();

        Assertions.assertEquals(callables.size(), futures.size());
    }

    @Test
    void submit_RunnableTask_AddsFutureToList(){

        List<Runnable> runnables = Arrays.asList(
                new DelayedRunnable(100),
                new DelayedRunnable(3000));

        for (var runn : runnables) {
            this.threadPool.submit(runn);
        }

        var futures = this.threadPool.getFutures();

        Assertions.assertEquals(runnables.size(), futures.size());
    }

    @Test
    void clearFuturesCache_clearFuturesList() {

        List<Callable<String>> callables = Arrays.asList(
                new DelayedCallable("fast thread", 100),
                new DelayedCallable("slow thread", 3000));

        for (var call : callables) {
            this.threadPool.submit(call);
        }

        var futures = this.threadPool.getFutures();
        Assertions.assertEquals(callables.size(), futures.size());

        this.threadPool.clearFuturesCache();

        futures = this.threadPool.getFutures();
        Assertions.assertEquals(0, futures.size());
    }

    private static class DelayedCallable implements Callable<String> {

        private final String name;
        private final long period;

        public DelayedCallable(String name, long period) {
            this.name = name;
            this.period = period;
        }

        public String call() {

            try {
                Thread.sleep(period);

            } catch (InterruptedException ex) {
                // handle exception
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }

            return name;
        }
    }

    private static class DelayedRunnable implements Runnable {

        private final long period;

        public DelayedRunnable(long period) {
            this.period = period;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(period);

            } catch (InterruptedException ex) {
                // handle exception
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}