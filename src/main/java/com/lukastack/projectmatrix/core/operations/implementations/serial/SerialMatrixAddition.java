package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.SerialOperationAddition;

public class SerialMatrixAddition extends SerialMatrixOperation
        implements SerialOperationAddition {

    public SerialMatrixAddition(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, Double::sum);

        return result;
    }

    @Override
    public Matrix add(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, Double::sum);

        return result;
    }
}
