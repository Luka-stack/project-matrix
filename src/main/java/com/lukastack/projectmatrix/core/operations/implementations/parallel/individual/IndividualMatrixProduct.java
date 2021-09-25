package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.operations.implementations.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.OperationProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.tasks.IndividualMatrixProductTask;

import java.util.concurrent.*;

public final class IndividualMatrixProduct extends MatrixOperation
        implements OperationProduct {

    public IndividualMatrixProduct(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    // TODO needs throw error if rows/cols not correct
    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        for (int rowId = 0; rowId < rowsLeft; ++rowId) {
            for (int colId = 0; colId < columnsRight; ++colId) {
                IndividualMatrixProductTask task = new IndividualMatrixProductTask(
                        result, matLeft, matRight, rowId, colId
                );

                taskPool.submit(task);
            }
        }
        return result;
    }
}
