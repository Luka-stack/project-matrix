package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProductOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupRowMatrixProduct implements GroupMatrixProductOperation {

    private int maxGroupSize;

    public GroupRowMatrixProduct(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix result,
                        final ThreadPoolExecutor taskPool) {

        int rows = leftMatrix.shape()[0];
        int step = rows / maxGroupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < maxGroupSize; ++group) {
            taskPool.submit(
                    new ProductTask(result, leftMatrix, rightMatrix, startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == maxGroupSize - 2 ? rows : endIndex + step;
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

                for (int rightColumn = 0; rightColumn < matRight.shape()[1]; ++rightColumn) {

                    movingResult = 0;
                    for (int leftColumn = 0; leftColumn < matLeft.shape()[1]; ++leftColumn) {
                        movingResult += matLeft.get(startIndex, leftColumn) * matRight.get(leftColumn, rightColumn);
                    }

                    result.set(startIndex, rightColumn, movingResult);
                }
            }
        }
    }
}
