package com.lukastack.projectmatrix.api.old.selective;

import com.lukastack.projectmatrix.api.old.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.MatrixProduct;

public abstract class SelectiveOperationsBundle implements IOperationsBundle {

    protected MatrixProduct matrixProductImpl;

    protected SelectiveOperationsBundle(final MatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }

    public void setMatrixProduct(MatrixProduct matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }
}
