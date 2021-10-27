package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProduct;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupColumnMatrixProduct implements GroupMatrixProduct {

    private int maxGroupSize;

    public GroupColumnMatrixProduct() {

        this.maxGroupSize = -1;
    }

    public GroupColumnMatrixProduct(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        int columns = result.shape()[1];
        int groupSize;

        if (this.maxGroupSize == -1) {
            groupSize = (int) Math.pow(Math.E, Math.log10(columns));
        }
        else {
            groupSize = this.maxGroupSize;
        }

        int step = columns / groupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < groupSize; ++group) {
            taskPool.submit(
                    new ProductTask(result, leftMatrix, rightMatrix, startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == groupSize - 2 ? columns : endIndex + step;
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
                    for (int rightMovingRow = 0; rightMovingRow < matRight.shape()[0]; ++rightMovingRow) {
                        movingResult += matLeft.get(leftMovingRow, rightMovingRow) * matRight.get(rightMovingRow, startIndex);
                    }

                    result.set(leftMovingRow, startIndex, movingResult);
                }
            }
        }
    }
}
