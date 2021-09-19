package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixSubtraction;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixSubtractionImpl<E extends Matrix> implements IParallelMatrixSubtraction, ParallelElementWiseMatrixEquation {

    private final Class<E> clazz;

    public IndividualMatrixSubtractionImpl(Class<E> clazz) {

        this.clazz = clazz;
    }

    @Override
    public Matrix sub(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a - b);

        return  result;
    }

    @Override
    public Matrix sub(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        Matrix promotedScalar = createMatrixWithDefaultValue(shape[0], shape[1], scalar);

        this.operate(leftMatrix, promotedScalar, result, taskPool, (a, b) -> a - b);

        return  result;
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

    private Matrix createMatrixWithDefaultValue(int rows, int cols, double defaultValue) {

        var result = this.createMatrix(rows, cols);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i, j, defaultValue);
            }
        }

        return result;
    }
}
