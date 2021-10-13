package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.element;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProductOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupElementMatrixProduct implements GroupMatrixProductOperation {

    private int maxGroupSize;

    public GroupElementMatrixProduct() {

        this.maxGroupSize = -1;
    }

    public GroupElementMatrixProduct(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result, ThreadPoolExecutor taskPool) {

        var shape = result.shape();
        int elements = shape[0] * shape[1];
        int groupSize;

        if (this.maxGroupSize == -1) {
            groupSize = (int) Math.pow(Math.E, Math.log10(elements));
        }
        else {
            groupSize = this.maxGroupSize;
        }

        int step = elements / groupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < groupSize; ++group) {
            taskPool.submit(
                    new ProductTask(result, leftMatrix, rightMatrix, startIndex, endIndex)
            );

            startIndex = endIndex;
            endIndex = group == groupSize - 2 ? elements : endIndex + step;
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
