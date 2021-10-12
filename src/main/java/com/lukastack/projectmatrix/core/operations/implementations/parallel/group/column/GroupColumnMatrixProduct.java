package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProductOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupColumnMatrixProduct implements GroupMatrixProductOperation {

    private int maxGroupSize;

    public GroupColumnMatrixProduct(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        int columns = leftMatrix.shape()[1];
        int step = columns / maxGroupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < maxGroupSize; ++group) {
            taskPool.submit(
                    new ProductTask(result, leftMatrix, rightMatrix, startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == maxGroupSize - 2 ? columns : endIndex + step;
        }
    }

    private static class ProductTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private int startIndex;
        private final int endIndex;

        public ProductTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                       int startIndex, int endIndex) {

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;

            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {

            double movingResult;
            for (; startIndex < endIndex; ++startIndex) {

                for (int leftMovingRow = 0; leftMovingRow < matLeft.shape()[0]; ++leftMovingRow) {

                    movingResult = 0;
                    for (int rightMovingRow = 0; rightMovingRow < matLeft.shape()[1]; ++rightMovingRow) {
                        movingResult += matLeft.get(rightMovingRow, startIndex) * matRight.get(rightMovingRow, leftMovingRow);
                    }

                    result.set(leftMovingRow, startIndex, movingResult);
                }
            }
        }
    }
}
