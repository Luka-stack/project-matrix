package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.ISerialMatrixProduct;

import java.lang.reflect.InvocationTargetException;

public class SerialMatrixProduct<E extends Matrix> implements ISerialMatrixProduct {

    private final Class<E> clazz;

    public SerialMatrixProduct(Class<E> clazz) {

        this.clazz = clazz;
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

    // TODO this method is and will be across all implementation, should be extracted somewhere
    private Matrix createMatrix(int rows, int cols) {

        try {
            return clazz.getDeclaredConstructor(int.class, int.class)
                    .newInstance(rows, cols);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Implementation of a Matrix class must have constructor with 2 integer parameters");
        }
    }

}
