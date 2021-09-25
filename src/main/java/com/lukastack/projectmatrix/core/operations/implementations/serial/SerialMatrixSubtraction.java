package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.operations.implementations.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.SerialOperationSubtraction;

public class SerialMatrixSubtraction extends MatrixOperation
        implements SerialOperationSubtraction {

    public SerialMatrixSubtraction(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    @Override
    public Matrix sub(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, (a, b) -> a - b);

        return result;
    }

    @Override
    public Matrix sub(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, (a, b) -> a - b);

        return result;
    }
}