package com.lukastack.projectmatrix.core.operations.api.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialRoot;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import com.lukastack.projectmatrix.utils.NthRoot;

public class SerialMatrixRoot extends com.lukastack.projectmatrix.core.operations.api.serial.SerialMatrixOperation
        implements SerialRoot {

    private final SerialMatrixOperation matrixOperations;

    public SerialMatrixRoot(final Class<? extends Matrix> clazz) {
        super(clazz);

        this.matrixOperations = new SerialDefaultOperation();
    }

    public SerialMatrixRoot(final Class<? extends Matrix> clazz,
                             final SerialMatrixOperation matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix root(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, NthRoot::nthRoot);

        return result;
    }

    @Override
    public Matrix root(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(matrix, scalar, result, NthRoot::nthRoot);

        return result;
    }
}
