package com.lukastack.projectmatrix.core.matrices;

import com.lukastack.projectmatrix.errors.CreationalException;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.errors.ImplementationException;

import java.lang.reflect.InvocationTargetException;

public abstract class Matrix implements IMatrix {

    Matrix() {

        try {
            this.getClass().getConstructor(int.class, int.class);
        } catch (NoSuchMethodException e) {
            throw new ImplementationException(
                    "Any class that extends Matrix has to implement constructor with two int parameters - constructor(int, int)"
            );
        }
    }

    public static Matrix buildMatrix(Class<? extends Matrix> clazz, int rows, int cols) {

        try {
            return clazz.getDeclaredConstructor(int.class, int.class)
                    .newInstance(rows, cols);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new CreationalException(String.format("Cannot create new instance of class %s", clazz.getName()));
        }
    }

    void assertDimensionNotZero(int rows, int cols) {

        if (rows == 0 || cols == 0) {
            throw new DimensionException("Cannot create Matrix with one of its dimensions equal to 0");
        }
    }
}
