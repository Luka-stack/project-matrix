package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperations;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialOperations;

public abstract class SerialMatrixOperation implements CreateMatrix {

    protected final Class<? extends Matrix> clazz;

    protected SerialMatrixOperation(final Class<? extends Matrix> clazz) {
        this.clazz = clazz;
    }

    protected Matrix createMatrix(int rows, int cols) {

        return this.createMatrix(clazz, rows, cols);
    }
}
