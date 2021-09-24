package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.GenericEquation;
import com.lukastack.projectmatrix.core.parallel.individual.tasks.IndividualMatrixVMatrixOperationTask;
import com.lukastack.projectmatrix.core.parallel.individual.tasks.IndividualMatrixVScalarOperationTask;

import java.util.concurrent.ThreadPoolExecutor;

public interface ParallelElementWiseMatrixEquation {

    default void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                         final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        int[] shape = leftMatrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                IndividualMatrixVMatrixOperationTask task = new IndividualMatrixVMatrixOperationTask(
                        resultMatrix, leftMatrix, rightMatrix, genericEquation, row, col
                );

                taskPool.submit(task);
            }
        }
    }

    default void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                         final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        int[] shape = matrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                IndividualMatrixVScalarOperationTask task = new IndividualMatrixVScalarOperationTask(
                        resultMatrix, matrix, scalar, genericEquation, row, col
                );

                taskPool.submit(task);
            }
        }
    }
}
