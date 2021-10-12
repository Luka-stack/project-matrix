package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupColumnOperations implements GroupMatrixOperations {

    private int maxGroupSize;

    public GroupColumnOperations(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        int columns = leftMatrix.shape()[1];
        int step = columns / maxGroupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < maxGroupSize; ++group) {
            taskPool.submit(
                    new MatrixWithMatrixTask(resultMatrix, leftMatrix, rightMatrix, genericEquation,
                            startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == maxGroupSize - 2 ? columns : endIndex + step;
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        int columns = matrix.shape()[1];
        int step = columns / maxGroupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < maxGroupSize; ++group) {
            taskPool.submit(
                    new MatrixWithScalarTask(resultMatrix, matrix, scalar, genericEquation,
                            startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == maxGroupSize - 2 ? columns : endIndex + step;
        }
    }

    private static class MatrixWithMatrixTask implements Runnable {

        private final Matrix result;
        private final Matrix leftMatrix;
        private final Matrix rightMatrix;

        private final GenericEquation equation;

        private int startIndex;
        private final int endIndex;

        public MatrixWithMatrixTask(final Matrix result, final Matrix leftMatrix, final Matrix rightMatrix,
                                                final GenericEquation equation, int startIndex, int endIndex) {
            this.result = result;
            this.leftMatrix = leftMatrix;
            this.rightMatrix = rightMatrix;

            this.equation = equation;

            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {

            for (; startIndex < endIndex; ++startIndex) {

                for (int row = 0; row < leftMatrix.shape()[0]; ++row) {
                    result.set(row, startIndex,
                            equation.operate(leftMatrix.get(row, startIndex), rightMatrix.get(row, startIndex)));
                }
            }
        }
    }

    private static class MatrixWithScalarTask implements Runnable {

        private final Matrix result;
        private final Matrix matrix;
        private final double scalar;

        private final GenericEquation equation;

        private int startIndex;
        private final int endIndex;

        public MatrixWithScalarTask(final Matrix result, final Matrix matrix, double scalar,
                                    final GenericEquation equation, int startIndex, int endIndex) {
            this.result = result;
            this.matrix = matrix;
            this.scalar = scalar;

            this.equation = equation;

            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {

            for (; startIndex < endIndex; ++startIndex) {

                for (int row = 0; row < matrix.shape()[0]; ++row) {
                    result.set(row, startIndex,
                            equation.operate(matrix.get(row, startIndex), scalar));
                }
            }
        }
    }

}
