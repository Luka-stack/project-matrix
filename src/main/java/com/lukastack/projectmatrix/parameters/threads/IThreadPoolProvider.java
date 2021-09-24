package com.lukastack.projectmatrix.parameters.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public interface IThreadPoolProvider {

    ThreadPoolExecutor provideThreadPool();

    void close() throws InterruptedException;

    void close(int timeout, TimeUnit unit) throws InterruptedException;

    void waitForCompletion() throws InterruptedException, ExecutionException;
}
