package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.column;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixProductOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisColumnProduct implements AxisMatrixProductOperation {

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        for (int column = 0; column < rightMatrix.shape()[1]; ++column) {

            taskPool.submit(
                    new AxisColumnProductTask(result, leftMatrix, rightMatrix, column)
            );
        }
    }

    private static class AxisColumnProductTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final int column;

        private AxisColumnProductTask(Matrix result, Matrix matLeft, Matrix matRight, int column) {
            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;
            this.column = column;
        }

        @Override
        public void run() {

            double movingValue;

            for (int leftMovingRow = 0; leftMovingRow < matLeft.shape()[0]; ++leftMovingRow) {

                movingValue = 0;
                for (int rightMovingRow = 0; rightMovingRow < matRight.shape()[0]; ++rightMovingRow) {

                    movingValue += matLeft.get(leftMovingRow, rightMovingRow) * matRight.get(rightMovingRow, column);
                }

                result.set(leftMovingRow, column, movingValue);
            }
        }
    }
}
