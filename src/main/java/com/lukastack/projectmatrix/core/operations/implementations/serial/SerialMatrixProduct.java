package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.operations.implementations.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.SerialOperationProduct;

public class SerialMatrixProduct extends MatrixOperation
        implements SerialOperationProduct {

    public SerialMatrixProduct(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight) {

        int[] shape = matLeft.shape();
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(shape[0], columnsRight);

        for (int leftRow = 0; leftRow < shape[0]; ++leftRow) {
            for (int rightCol = 0; rightCol < columnsRight; ++rightCol) {

                for (int leftCol = 0; leftCol < shape[1]; ++leftCol) {
                    result.set(leftRow, rightCol,
                            result.get(leftRow, rightCol) +
                                    matLeft.get(leftRow, leftCol) * matRight.get(leftCol, rightCol));
                }
            }
        }

        return result;
    }
}
