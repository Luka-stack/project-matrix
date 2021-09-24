package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixProduct;
import com.lukastack.projectmatrix.parameters.threads.AbstractThreadPoolProvider;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final IParallelMatrixProduct matrixProductImpl;
    protected final AbstractThreadPoolProvider threadPoolProvider;

    protected OperationsBundle(IParallelMatrixProduct matrixProductImpl, AbstractThreadPoolProvider threadPoolProvider) {

        this.matrixProductImpl = matrixProductImpl;
        this.threadPoolProvider = threadPoolProvider;
    }
}
