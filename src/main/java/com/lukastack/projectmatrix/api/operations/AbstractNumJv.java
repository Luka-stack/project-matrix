package com.lukastack.projectmatrix.api.operations;

import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractNumJv implements CreateMatrix {

    private final Class<? extends Matrix> clazz;
    protected final ThreadPoolProvider poolProvider;
    protected final boolean closeOnFinish;
    protected final boolean waitForResult;
    protected ThreadPoolExecutor threadPoolExecutor = null;

    protected AbstractNumJv() {

        this.poolProvider = new SingletonThreadPoolProvider();
        this.closeOnFinish = true;
        this.waitForResult = true;
        this.clazz = MatJv.class;
    }

    protected AbstractNumJv(final Class<? extends Matrix> clazz, final ThreadPoolProvider threadPoolProvider,
                            boolean closeOnFinish, boolean waitForResult) {

        this.poolProvider = threadPoolProvider;
        this.closeOnFinish = closeOnFinish;
        this.waitForResult = waitForResult;
        this.clazz = clazz;
    }

    public void waitForResult() {

        try {
            poolProvider.waitForCompletion();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void closeThreadPool() {

        try {
            if (threadPoolExecutor != null) {
                poolProvider.close();
                threadPoolExecutor = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected Matrix createMatrix(int rows, int columns) {

        return createMatrix(clazz, rows, columns);
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
