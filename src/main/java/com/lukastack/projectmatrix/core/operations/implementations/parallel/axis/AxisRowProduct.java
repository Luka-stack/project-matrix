package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisRowProduct implements AxisMatrixProductOperation {

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        for (int row = 0; row < leftMatrix.shape()[0]; ++row) {

            taskPool.submit(
                    new AxisRowProductTask(result, leftMatrix, rightMatrix,row)
            );
        }
    }

    private static class AxisRowProductTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final int row;

        private AxisRowProductTask(Matrix result, Matrix matLeft, Matrix matRight, int row) {
            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;
            this.row = row;
        }

        @Override
        public void run() {

            double movingValue;

            for (int rightMovingCol = 0; rightMovingCol < matRight.shape()[1]; ++rightMovingCol) {

                movingValue = 0;
                for (int leftMovingCol = 0; leftMovingCol < matLeft.shape()[1]; ++leftMovingCol) {

                    movingValue += matLeft.get(row, leftMovingCol) * matRight.get(leftMovingCol, rightMovingCol);
                }

                result.set(row, rightMovingCol, movingValue);
            }
        }
    }
}
