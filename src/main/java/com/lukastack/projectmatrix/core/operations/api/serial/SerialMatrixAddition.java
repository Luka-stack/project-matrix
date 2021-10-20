package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialAddition;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;

public class SerialMatrixAddition extends com.lukastack.projectmatrix.core.operations.api.serial.SerialMatrixOperation
        implements SerialAddition {

    private final SerialMatrixOperation matrixOperations;

    public SerialMatrixAddition(final Class<? extends Matrix> clazz) {
        super(clazz);

        this.matrixOperations = new SerialDefaultOperation();
    }

    public SerialMatrixAddition(final Class<? extends Matrix> clazz,
                                final SerialMatrixOperation matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

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
