package com.lukastack.projectmatrix.core.matrices;

import java.lang.reflect.InvocationTargetException;

public class MatrixBuilder {

    private MatrixBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static Matrix buildMatrix(Class<? extends Matrix> clazz, int rows, int cols) {

        // TODO better error
        try {
            return clazz.getDeclaredConstructor(int.class, int.class)
                    .newInstance(rows, cols);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Implementation of a Matrix class must have constructor with 2 integer parameters");
        }
    }
}
