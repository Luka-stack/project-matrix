package com.lukastack.projectmatrix.parameters;

import java.util.concurrent.TimeUnit;

public interface IThreadPoolSettings {

    void setKeepAlive(long time, TimeUnit unit);

    void setCorePoolSize(int size);

    void setMaximumPoolSize(int size);

    long getKeepAlive();

    int getCorePoolSize();

    int getMaximumPoolSize();

    ThreadPoolType getThreadPoolType();

    TimeUnit getTimeUnit();

    void printSettings();
}