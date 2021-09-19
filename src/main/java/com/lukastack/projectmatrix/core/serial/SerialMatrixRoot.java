package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.serial.ISerialMatrixRoot;
import com.lukastack.projectmatrix.utils.NthRoot;

import java.lang.reflect.InvocationTargetException;

public class SerialMatrixRoot<E extends Matrix> implements ISerialMatrixRoot, SerialElementWiseMatrixEquation {

    private final Class<E> clazz;

    public SerialMatrixRoot(Class<E> clazz) {

        this.clazz = clazz;
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
