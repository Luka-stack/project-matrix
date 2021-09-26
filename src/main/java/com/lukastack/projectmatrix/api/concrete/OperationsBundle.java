package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.parallel.OperationProduct;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final OperationProduct matrixProductImpl;
    protected final ThreadPoolProvider threadPoolProvider;

    protected OperationsBundle(OperationProduct matrixProductImpl, ThreadPoolProvider threadPoolProvider) {

        this.matrixProductImpl = matrixProductImpl;
        this.threadPoolProvider = threadPoolProvider;
    }
}
