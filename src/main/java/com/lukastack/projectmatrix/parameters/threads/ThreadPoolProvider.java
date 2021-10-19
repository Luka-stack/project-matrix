package com.lukastack.projectmatrix.parameters.threads;

import java.util.concurrent.*;

public abstract class ThreadPoolProvider implements IThreadPoolProvider, ThreadPoolParameters {

    protected long keepAlive;
    protected TimeUnit keepAliveUnit;
    protected int corePoolSize;
    protected int maximumPoolSize;

    @Override
    public void setKeepAlive(long time, TimeUnit unit) {
        this.keepAlive = time;
        this.keepAliveUnit = unit;
    }

    @Override
    public void setCorePoolSize(int size) {
        this.corePoolSize = size;
    }

    @Override
    public void setMaximumPoolSize(int size) {
        this.maximumPoolSize = size;
    }

    @Override
    public long getKeepAlive() {
        return this.keepAlive;
    }

    @Override
    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    @Override
    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return keepAliveUnit;
    }

    @Override
    public void printSettings() {

    }

    protected BlockingQueue<Runnable> newWorkQueueInstance() {

        return new ArrayBlockingQueue<>(1000000);
    }
}
