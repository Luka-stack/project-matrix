package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.IMatrixProduct;

public abstract class OperationsBundle implements IOperationsBundle {

    protected final IMatrixProduct matrixProductImpl;

    protected OperationsBundle(IMatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }
}
