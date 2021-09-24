package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixPower;

public class SerialMatrixPower<E extends Matrix> extends MatrixOperationTemplate<E>
        implements ISerialMatrixPower {

    public SerialMatrixPower(Class<E> clazz) {

        super(clazz);
    }

    @Override
    public Matrix power(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, Math::pow);

        return result;
    }

    @Override
    public Matrix power(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, Math::pow);

        return result;
    }
}
