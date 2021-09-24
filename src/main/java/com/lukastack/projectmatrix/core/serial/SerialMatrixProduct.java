package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixProduct;

public class SerialMatrixProduct<E extends Matrix> extends MatrixOperationTemplate<E>
        implements ISerialMatrixProduct {

    public SerialMatrixProduct(Class<E> clazz) {

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
