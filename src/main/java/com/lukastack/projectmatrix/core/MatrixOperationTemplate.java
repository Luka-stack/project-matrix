package com.lukastack.projectmatrix.core;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.lang.reflect.InvocationTargetException;

public class MatrixOperationTemplate<E extends Matrix> implements ElementWiseMatrixEquation {

    protected final Class<E> clazz;

    protected MatrixOperationTemplate(Class<E> clazz) {
        this.clazz = clazz;
    }

    protected Matrix createMatrix(int rows, int cols) {

        try {
            return clazz.getDeclaredConstructor(int.class, int.class)
                    .newInstance(rows, cols);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Implementation of a Matrix class must have constructor with 2 integer parameters");
        }
    }
}
