package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixAddition;

public class SerialMatrixAddition<E extends Matrix> extends MatrixOperationTemplate<E>
        implements ISerialMatrixAddition {

    public SerialMatrixAddition(Class<E> clazz) {

        super(clazz);
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, Double::sum);

        return result;
    }

    @Override
    public Matrix add(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(matrix, scalar, result, Double::sum);

        return result;
    }
}
