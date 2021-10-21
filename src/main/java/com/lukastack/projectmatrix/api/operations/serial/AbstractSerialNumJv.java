package com.lukastack.projectmatrix.api.operations.serial;

import com.lukastack.projectmatrix.api.operations.specification.DefaultOperations;
import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.errors.DimensionException;

public abstract class AbstractSerialNumJv implements CreateMatrix, DefaultOperations {

    private final Class<? extends Matrix> clazz;

    protected AbstractSerialNumJv() {

        this.clazz = MatJv.class;
    }

    protected AbstractSerialNumJv(final Class<? extends Matrix> clazz) {

        this.clazz = clazz;
    }

    protected Matrix createMatrix(int rows, int columns) {

        return createMatrix(clazz, rows, columns);
    }

    protected void assertElementWiseDimension(final Matrix leftMatrix, final Matrix rightMatrix) {

        if (leftMatrix.shape()[0] != rightMatrix.shape()[0] || leftMatrix.shape()[1] != rightMatrix.shape()[1]) {
            throw new DimensionException(String.format("Matrices dimensions are not equal, (%d, %d) != (%d, %d)",
                    leftMatrix.shape()[0], leftMatrix.shape()[1], rightMatrix.shape()[0], rightMatrix.shape()[1]));
        }
    }

    protected void assertMatrixProductDimension(Matrix leftMatrix, Matrix rightMatrix) {

        if (leftMatrix.shape()[1] != rightMatrix.shape()[0]) {
            throw new DimensionException(
                    String.format("Left side Matrix's column size and right side Matrix's row size must be equal %d != %d",
                            leftMatrix.shape()[1], rightMatrix.shape()[0]));
        }
    }
}
