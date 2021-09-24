package com.lukastack.projectmatrix.api.selective;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixProduct;

public abstract class SelectiveOperationsBundle implements IOperationsBundle {

    protected IParallelMatrixProduct matrixProductImpl;

    protected SelectiveOperationsBundle(final IParallelMatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }

    public void setMatrixProduct(IParallelMatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }
}
