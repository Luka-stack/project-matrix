package com.lukastack.projectmatrix.parameters;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public abstract class AbstractThreadPoolProvider implements IThreadPoolProvider, IThreadPoolSettings {

    protected long keepAlive;
    protected TimeUnit keepAliveUnit;
    protected int corePoolSize;
    protected int maximumPoolSize;
    protected ThreadPoolType threadPoolType;
    protected Class<? extends BlockingQueue<Runnable>> workQueueClass;

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
    public ThreadPoolType getThreadPoolType() {
        return threadPoolType;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return keepAliveUnit;
    }

    @Override
    public void printSettings() {

    }

    protected BlockingQueue<Runnable> newWorkQueueInstance() {

        if (this.workQueueClass != null) {

            try {
                return workQueueClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("ThreadPoolProvider - Could not create a new workQueue instance");
            }
        }

        return this.threadPoolType == ThreadPoolType.FIXED ?
                new LinkedBlockingQueue<>() :
                new SynchronousQueue<>();
    }
}
