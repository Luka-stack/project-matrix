package com.lukastack.projectmatrix.api.selective;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.IMatrixProduct;

public abstract class SelectiveOperationsBundle implements IOperationsBundle {

    protected IMatrixProduct matrixProductImpl;

    protected SelectiveOperationsBundle(final IMatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }

    public void setMatrixProduct(IMatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }
}
