package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialPower;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperations;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialOperations;

public class SerialMatrixPower extends SerialMatrixOperation
        implements SerialPower {

    private final SerialMatrixOperations matrixOperations;

    public SerialMatrixPower(final Class<? extends Matrix> clazz) {
        super(clazz);

        this.matrixOperations = new SerialOperations();
    }

    public SerialMatrixPower(final Class<? extends Matrix> clazz,
                             final SerialMatrixOperations matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix power(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, Math::pow);

        return result;
    }

    @Override
    public Matrix power(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(matrix, scalar, result, Math::pow);

        return result;
    }
}
