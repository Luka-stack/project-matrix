package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixMultiplication;

public class SerialMatrixMultiplication<E extends Matrix> extends MatrixOperationTemplate<E>
        implements ISerialMatrixMultiplication {

    public SerialMatrixMultiplication(Class<E> clazz) {

        super(clazz);
    }

    @Override
    public Matrix multiply(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, (a, b) -> a * b);

        return result;
    }

    @Override
    public Matrix multiply(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, (a, b) -> a * b);

        return result;
    }
}
