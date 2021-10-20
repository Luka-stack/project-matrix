package com.lukastack.projectmatrix.api.old.concrete;

import com.lukastack.projectmatrix.api.old.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.MatrixProduct;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final MatrixProduct matrixProductImpl;
    protected final ThreadPoolProvider threadPoolProvider;

    protected OperationsBundle(MatrixProduct matrixProductImpl, ThreadPoolProvider threadPoolProvider) {

        this.matrixProductImpl = matrixProductImpl;
        this.threadPoolProvider = threadPoolProvider;
    }
}
