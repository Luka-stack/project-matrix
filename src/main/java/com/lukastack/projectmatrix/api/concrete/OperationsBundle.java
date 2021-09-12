package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.IMatrixProduct;
import com.lukastack.projectmatrix.parameters.IThreadPoolProvider;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final IMatrixProduct matrixProductImpl;
    protected final IThreadPoolProvider threadPoolProvider;

    protected OperationsBundle(IMatrixProduct matrixProductImpl, IThreadPoolProvider threadPoolProvider) {

        this.matrixProductImpl = matrixProductImpl;
        this.threadPoolProvider = threadPoolProvider;
    }
}
