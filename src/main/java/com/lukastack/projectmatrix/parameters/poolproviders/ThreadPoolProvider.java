package com.lukastack.projectmatrix.parameters.poolproviders;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public abstract class ThreadPoolProvider implements IThreadPoolProvider, ThreadPoolParameters {

    private Class<? extends BlockingQueue<Runnable>> workQueueClass;

    protected long keepAlive;
    protected TimeUnit keepAliveUnit;
    protected int corePoolSize;
    protected int maximumPoolSize;
    protected ThreadPoolType threadPoolType;

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

    public <E extends BlockingQueue<Runnable>> void setWorkQueueClass(Class<E> workQueueClass) {

        this.workQueueClass = workQueueClass;
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

        if (threadPoolType.equals(ThreadPoolType.CACHED)) {
            return new SynchronousQueue<>();
        }
        else if (threadPoolType.equals(ThreadPoolType.FIXED)) {
            return new LinkedBlockingQueue<>();
        }
        else {
            try {
                return workQueueClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("ThreadPoolProvider - Could not create a new workQueue instance");
            }
        }
    }
}
