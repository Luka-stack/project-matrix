package com.lukastack.projectmatrix.parameters.poolproviders;

import java.util.concurrent.TimeUnit;

public interface ThreadPoolParameters {

    void setKeepAlive(long time, TimeUnit unit);

    void setCorePoolSize(int size);

    void setMaximumPoolSize(int size);

    long getKeepAlive();

    int getCorePoolSize();

    int getMaximumPoolSize();

    TimeUnit getTimeUnit();

    void printSettings();
}
