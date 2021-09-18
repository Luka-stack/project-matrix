package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.IMatrixProduct;
import com.lukastack.projectmatrix.parameters.threads.AbstractThreadPoolProvider;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final IMatrixProduct matrixProductImpl;
    protected final AbstractThreadPoolProvider threadPoolProvider;

    protected OperationsBundle(IMatrixProduct matrixProductImpl, AbstractThreadPoolProvider threadPoolProvider) {

        this.matrixProductImpl = matrixProductImpl;
        this.threadPoolProvider = threadPoolProvider;
    }
}
