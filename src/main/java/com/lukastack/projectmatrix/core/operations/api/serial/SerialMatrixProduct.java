package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultMatrixProduct;
import com.lukastack.projectmatrix.errors.DimensionException;

public class SerialMatrixProduct extends MatrixOperation
        implements SerialProduct {

    private final com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixProduct matrixOperation;

    public SerialMatrixProduct(final Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperation = new SerialDefaultMatrixProduct();
    }

    public SerialMatrixProduct(final Class<? extends Matrix> clazz,
                               final com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixProduct matrixOperation) {

        super(clazz);

        this.matrixOperation = matrixOperation;
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight) {

        this.assertCorrectDimension(matLeft, matRight);

        int[] shape = matLeft.shape();
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(shape[0], columnsRight);

        this.matrixOperation.operate(matLeft, matRight, result);

        return result;
    }

    @Override
    protected void assertCorrectDimension(Matrix leftMatrix, Matrix rightMatrix) {

        if (leftMatrix.shape()[1] != rightMatrix.shape()[0]) {
            throw new DimensionException(
                    String.format("Left side Matrix's column size and right side Matrix's row size must be equal %d != %d",
                            leftMatrix.shape()[1], rightMatrix.shape()[0]));
        }
    }
}
