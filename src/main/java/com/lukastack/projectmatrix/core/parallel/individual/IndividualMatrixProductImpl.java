package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.IMatrixProduct;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class IndividualMatrixProductImpl<E extends Matrix> implements IMatrixProduct {

    private final ExecutorService taskPool;
    private final Class<E> clazz;

    public IndividualMatrixProductImpl(Class<E> clazz) {

        this.clazz = clazz;
        this.taskPool = Executors.newFixedThreadPool(20);
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight) {

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        for (int rowId = 0; rowId < rowsLeft; ++rowId) {
            for (int colId = 0; colId < columnsRight; ++colId) {
                IndividualMatrixProductTask task = new IndividualMatrixProductTask(
                        result, matLeft, matRight, rowId, colId
                );

                taskPool.execute(task);
            }
        }

        taskPool.shutdown();

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
