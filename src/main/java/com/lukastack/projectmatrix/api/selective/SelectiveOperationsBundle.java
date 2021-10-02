package com.lukastack.projectmatrix.api.selective;

import com.lukastack.projectmatrix.api.IOperationsBundle;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Product;

public abstract class SelectiveOperationsBundle implements IOperationsBundle {

    protected Product matrixProductImpl;

    protected SelectiveOperationsBundle(final Product matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }

    public void setMatrixProduct(Product matrixProductImpl) {

        this.matrixProductImpl = matrixProductImpl;
    }
}
