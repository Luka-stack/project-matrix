package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixAddition;

import java.lang.reflect.InvocationTargetException;

public class SerialMatrixAddition<E extends Matrix> implements ISerialMatrixAddition, SerialElementWiseMatrixEquation {

    private final Class<E> clazz;

    public SerialMatrixAddition(Class<E> clazz) {

        this.clazz = clazz;
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

        this.operate(matrix, scalar, result, (a, b) -> a - b);

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
