package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.serial.SerialRoot;
import com.lukastack.projectmatrix.utils.NthRoot;

public class SerialMatrixRoot extends SerialMatrixOperation
        implements SerialRoot {

    public SerialMatrixRoot(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    @Override
    public Matrix root(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, NthRoot::nthRoot);

        return result;
    }

    @Override
    public Matrix root(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, NthRoot::nthRoot);

        return result;
    }
}
