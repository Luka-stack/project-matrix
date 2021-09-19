package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.serial.GenericEquation;

import java.util.concurrent.ThreadPoolExecutor;

public interface ParallelElementWiseMatrixEquation {

    default void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix resultMatrix, ThreadPoolExecutor taskPool,
                         GenericEquation genericEquation) {

        int[] shape = leftMatrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                IndividualMatrixOperationTask task = new IndividualMatrixOperationTask(
                        resultMatrix, leftMatrix, rightMatrix, genericEquation, row, col
                );

                taskPool.submit(task);
            }
        }
    }
}
