package com.lukastack.projectmatrix.api.selective;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.parallel.OperationProduct;

public abstract class SelectiveOperationsBundle implements IOperationsBundle {

    protected OperationProduct matrixProductImpl;

    protected SelectiveOperationsBundle(final OperationProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }

    public void setMatrixProduct(OperationProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }
}
