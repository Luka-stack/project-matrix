package com.lukastack.projectmatrix.parameters.poolproviders;

import java.util.concurrent.*;

public abstract class ThreadPoolProvider implements IThreadPoolProvider, ThreadPoolParameters {

    protected long keepAlive;
    protected TimeUnit keepAliveUnit;
    protected int corePoolSize;
    protected int maximumPoolSize;

    @Override
    public void setKeepAlive(long time, TimeUnit unit) {

        if (time < 0) {
            throw new IllegalArgumentException("Provided time cannot be negative");
        }

        this.keepAlive = time;
        this.keepAliveUnit = unit;
    }

    @Override
    public void setCorePoolSize(int size) {

        if (size < 0) {
            throw new IllegalArgumentException("Cole pool size cannot be negative");
        }

        this.corePoolSize = size;
    }

    @Override
    public void setMaximumPoolSize(int size) {

        if (size < 0) {
            throw new IllegalArgumentException("Cole pool size cannot be negative");
        }

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
    public String toString() {
        return "ThreadPoolProvider{" +
                "keepAlive=" + keepAlive +
                ", keepAliveUnit=" + keepAliveUnit +
                ", corePoolSize=" + corePoolSize +
                ", maximumPoolSize=" + maximumPoolSize +
                '}';
    }

    protected BlockingQueue<Runnable> newWorkQueueInstance() {

        return new ArrayBlockingQueue<>(1000000);
    }
}
