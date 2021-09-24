package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixDivision;

public class SerialMatrixDivision<E extends Matrix> extends MatrixOperationTemplate<E>
        implements ISerialMatrixDivision {

    public SerialMatrixDivision(Class<E> clazz) {

        super(clazz);
    }

    @Override
    public Matrix divide(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, (a, b) -> a / b);

        return result;
    }

    @Override
    public Matrix divide(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, (a, b) -> a / b);

        return result;
    }
}
