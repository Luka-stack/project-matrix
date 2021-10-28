package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualDefaultMatrixProduct implements IndividualMatrixProduct {

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix result,
                        final ThreadPoolExecutor taskPool) {

        for (int rowId = 0; rowId < leftMatrix.shape()[0]; ++rowId) {

            for (int colId = 0; colId < rightMatrix.shape()[1]; ++colId) {

                taskPool.submit(new IndividualProductTask(
                        result, leftMatrix, rightMatrix, rowId, colId
                ));
            }
        }
    }

    private static class IndividualProductTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final int row;
        private final int column;

        public IndividualProductTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                     int row, int column) {

            this.row = row;
            this.column = column;

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;
        }

        @Override
        public void run() {

            double movingResult = 0;

            for (int moving = 0; moving < matLeft.shape()[1]; ++moving) {
                movingResult += matLeft.get(row, moving) * matRight.get(moving, column);
            }

            result.set(row, column, movingResult);
        }
    }
}
