package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Product;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final Product matrixProductImpl;
    protected final ThreadPoolProvider threadPoolProvider;

    protected OperationsBundle(Product matrixProductImpl, ThreadPoolProvider threadPoolProvider) {

        this.matrixProductImpl = matrixProductImpl;
        this.threadPoolProvider = threadPoolProvider;
    }
}
