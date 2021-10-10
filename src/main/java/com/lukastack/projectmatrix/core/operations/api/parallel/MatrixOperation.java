package com.lukastack.projectmatrix.core.operations.api.parallel;

import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.errors.DimensionException;

public abstract class MatrixOperation implements CreateMatrix {

    protected final Class<? extends Matrix> clazz;

    protected MatrixOperation(Class<? extends Matrix> clazz) {
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
