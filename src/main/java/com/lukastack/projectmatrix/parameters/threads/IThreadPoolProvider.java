package com.lukastack.projectmatrix.parameters.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public interface IThreadPoolProvider {

    ThreadPoolExecutor provideThreadPool();

    void close();

    void waitForCompletion() throws InterruptedException, ExecutionException;
}
