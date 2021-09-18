package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.ISerialMatrixMultiplication;

import java.lang.reflect.InvocationTargetException;

public class SerialMatrixMultiplication<E extends Matrix> implements ISerialMatrixMultiplication {

    private final Class<E> clazz;

    public SerialMatrixMultiplication(Class<E> clazz) {

        this.clazz = clazz;
    }


    @Override
    public Matrix multiply(Matrix leftMatrix, Matrix rightMatrix) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                result.set(row, col,
                        leftMatrix.get(row, col) * rightMatrix.get(row, col));
            }
        }

        return result;
    }

    @Override
    public Matrix multiply(Matrix matrix, double scalar) {

        int[] shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                result.set(row, col,
                        matrix.get(row, col) * scalar);
            }
        }

        return result;
    }

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
