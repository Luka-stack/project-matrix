package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixProductOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialProductOperation;

public class SerialMatrixProduct extends MatrixOperation
        implements SerialProduct {

    private final SerialMatrixProductOperation matrixOperation;

    public SerialMatrixProduct(final Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperation = new SerialProductOperation();
    }

    public SerialMatrixProduct(final Class<? extends Matrix> clazz,
                               final SerialMatrixProductOperation matrixOperation) {

        super(clazz);

        this.matrixOperation = matrixOperation;
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight) {

        int[] shape = matLeft.shape();
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(shape[0], columnsRight);

        this.matrixOperation.operate(matLeft, matRight, result);

        return result;
    }
}
