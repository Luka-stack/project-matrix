package com.lukastack.projectmatrix.api.operations.parallel;

import com.lukastack.projectmatrix.api.operations.specification.DefaultOperations;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.matrices.MatrixBuilder;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.poolproviders.ThreadPoolProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractNumJv implements DefaultOperations {

    private final Class<? extends Matrix> clazz;
    protected final ThreadPoolProvider poolProvider;
    protected ThreadPoolExecutor threadPoolExecutor = null;

    protected AbstractNumJv() {

        this.poolProvider = new SingletonThreadPoolProvider();
        this.clazz = MatJv.class;
    }

    protected AbstractNumJv(final Class<? extends Matrix> clazz, final ThreadPoolProvider threadPoolProvider) {

        this.poolProvider = threadPoolProvider;
        this.clazz = clazz;
    }

    public void waitForResult() {

        try {
            poolProvider.waitForCompletion();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void closeThreadPool() {

        try {
            if (threadPoolExecutor != null) {
                poolProvider.close();
                threadPoolExecutor = null;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected Matrix createMatrix(int rows, int columns) {

        return MatrixBuilder.buildMatrix(clazz, rows, columns);
    }

    protected void assertElementWiseDimension(final Matrix leftMatrix, final Matrix rightMatrix) {

        if (leftMatrix.shape()[0] != rightMatrix.shape()[0] || leftMatrix.shape()[1] != rightMatrix.shape()[1]) {
            throw new DimensionException(String.format("Matrices dimensions are not equal, (%d, %d) != (%d, %d)",
                    leftMatrix.shape()[0], leftMatrix.shape()[1], rightMatrix.shape()[0], rightMatrix.shape()[1]));
        }
    }

    protected void assertMatrixProductDimension(Matrix leftMatrix, Matrix rightMatrix) {

        if (leftMatrix.shape()[1] != rightMatrix.shape()[0]) {
            throw new DimensionException(
                    String.format("Left side Matrix's column size and right side Matrix's row size must be equal %d != %d",
                            leftMatrix.shape()[1], rightMatrix.shape()[0]));
        }
    }
}
