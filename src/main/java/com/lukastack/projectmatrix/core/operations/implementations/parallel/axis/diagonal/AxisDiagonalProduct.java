package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.diagonal;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixProductOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisDiagonalProduct implements AxisMatrixProductOperation {

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        var shape = result.shape();

        for (int column = 0; column < shape[1]; ++column) {
            taskPool.submit(new AxisDiagonalProductTask(result, leftMatrix, rightMatrix, 0, column));
        }

        for (int row = 1; row < shape[0]; ++row) {
            taskPool.submit(new AxisDiagonalProductTask(result, leftMatrix, rightMatrix, row, 0));
        }
    }

    private static class AxisDiagonalProductTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private int row;
        private int column;

        public AxisDiagonalProductTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                       int row, int column) {

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;

            this.row = row;
            this.column = column;
        }

        @Override
        public void run() {

            double movingValue;
            var resultShape = result.shape();

            for (; row < resultShape[0] && column < resultShape[1]; ++row, ++column) {

                movingValue = 0;
                for(int movingShareSize = 0; movingShareSize < matLeft.shape()[1]; ++movingShareSize) {

                    movingValue += matLeft.get(row, movingShareSize) * matRight.get(movingShareSize, column);
                }

                result.set(row, column, movingValue);
            }
        }
    }
}
