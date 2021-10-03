package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialAddition;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperations;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialOperations;

public class SerialMatrixAddition extends SerialMatrixOperation
        implements SerialAddition {

    private final SerialMatrixOperations matrixOperations;

    public SerialMatrixAddition(final Class<? extends Matrix> clazz) {
        super(clazz);

        this.matrixOperations = new SerialOperations();
    }

    public SerialMatrixAddition(final Class<? extends Matrix> clazz,
                                final SerialMatrixOperations matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, Double::sum);

        return result;
    }

    @Override
    public Matrix add(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(matrix, scalar, result, Double::sum);

        return result;
    }
}
