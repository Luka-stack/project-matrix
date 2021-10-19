package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperations;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialOperations;
import com.lukastack.projectmatrix.errors.DimensionException;

public abstract class SerialMatrixOperation implements CreateMatrix {

    protected final Class<? extends Matrix> clazz;

    protected SerialMatrixOperation(final Class<? extends Matrix> clazz) {
        this.clazz = clazz;
    }

    protected Matrix createMatrix(int rows, int cols) {

        return this.createMatrix(clazz, rows, cols);
    }

    protected void assertCorrectDimension(final Matrix leftMatrix, final Matrix rightMatrix) {

        if (leftMatrix.shape()[0] != rightMatrix.shape()[0] || leftMatrix.shape()[1] != rightMatrix.shape()[1]) {
            throw new DimensionException(String.format("Matrices dimensions are not equal, (%d, %d) != (%d, %d)",
                    leftMatrix.shape()[0], leftMatrix.shape()[1], rightMatrix.shape()[0], rightMatrix.shape()[1]));
        }
    }
}