package com.lukastack.projectmatrix.core.matrices;

import java.lang.reflect.InvocationTargetException;

public interface CreateMatrixOld {

    default Matrix createMatrix(Class<? extends Matrix> clazz, int rows, int cols) {

        try {
            return clazz.getDeclaredConstructor(int.class, int.class)
                    .newInstance(rows, cols);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Implementation of a Matrix class must have constructor with 2 integer parameters");
        }
    }
}
