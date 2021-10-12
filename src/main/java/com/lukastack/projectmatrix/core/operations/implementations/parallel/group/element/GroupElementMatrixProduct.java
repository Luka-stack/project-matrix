package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.element;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProductOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupElementMatrixProduct implements GroupMatrixProductOperation {

    private int maxGroupSize;

    public GroupElementMatrixProduct(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        var shape = result.shape();
        int elements = shape[0] * shape[1];
        int step = elements / maxGroupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < maxGroupSize; ++group) {
            taskPool.submit(
                    new ProductTask(result, leftMatrix, rightMatrix, startIndex, endIndex)
            );

            startIndex = endIndex;
            endIndex = group == maxGroupSize - 2 ? elements : endIndex + step;
        }
    }

    private static class ProductTask implements Runnable {

        private final Matrix result;
        private final Matrix leftMatrix;
        private final Matrix rightMatrix;

        private int startIndex;
        private final int endIndex;

        public ProductTask(final Matrix result, final Matrix leftMatrix, final Matrix rightMatrix,
                           int startIndex, int endIndex) {

            this.result = result;
            this.leftMatrix = leftMatrix;
            this.rightMatrix = rightMatrix;

            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {

            var shape = result.shape();
            int row;
            int column;
            double movingResult;

            for (; startIndex < endIndex; ++startIndex) {
                column = (startIndex) % shape[1];
                row = (startIndex / shape[1]) % shape[0];
                movingResult = 0;

                for (int sharedDim = 0; sharedDim < leftMatrix.shape()[1]; ++sharedDim) {
                    movingResult += leftMatrix.get(row, sharedDim) * rightMatrix.get(sharedDim, column);
                    result.set(row, column, movingResult);
                }
            }
        }
    }
}
