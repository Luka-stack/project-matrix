package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.operations.IMatrixProduct;
import com.lukastack.projectmatrix.matrices.MatJv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class IndividualMatrixProductImpl implements IMatrixProduct {

    private final ExecutorService taskPool = Executors.newFixedThreadPool(100);

    @Override
    public MatJv matMul(MatJv matLeft, MatJv matRight) {

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        MatJv result = new MatJv(rowsLeft, columnsRight);

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
}
