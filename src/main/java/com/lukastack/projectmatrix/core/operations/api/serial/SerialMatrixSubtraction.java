package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialSubtraction;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;

public class SerialMatrixSubtraction extends com.lukastack.projectmatrix.core.operations.api.serial.SerialMatrixOperation
        implements SerialSubtraction {

    private final SerialMatrixOperation matrixOperations;

    public SerialMatrixSubtraction(final Class<? extends Matrix> clazz) {
        super(clazz);

        this.matrixOperations = new SerialDefaultOperation();
    }

    public SerialMatrixSubtraction(final Class<? extends Matrix> clazz,
                            final SerialMatrixOperation matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix sub(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, (a, b) -> a - b);

        return result;
    }

    @Override
    public Matrix sub(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(matrix, scalar, result, (a, b) -> a - b);

        return result;
    }
}
