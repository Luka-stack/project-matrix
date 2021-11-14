package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.element;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupElementOperation implements GroupMatrixOperation {

    private int maxGroupSize;

    public GroupElementOperation() {

        this.maxGroupSize = -1;
    }

    public GroupElementOperation(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        var shape = resultMatrix.shape();
        int elements = shape[0] * shape[1];
        int groupSize;

        if (this.maxGroupSize == -1) {
            groupSize = Math.min((int) Math.pow(Math.E, Math.log10(elements)), Runtime.getRuntime().availableProcessors());
        }
        else {
            groupSize = this.maxGroupSize;
        }

        int step = elements / groupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < groupSize; ++group) {
            taskPool.submit(
                    new MatrixWithMatrixTask(resultMatrix, leftMatrix, rightMatrix, genericEquation,
                            startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == groupSize - 2 ? elements : endIndex + step;
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        var shape = resultMatrix.shape();
        int elements = shape[0] * shape[1];
        int groupSize;

        if (this.maxGroupSize == -1) {
            groupSize = Math.min((int) Math.pow(Math.E, Math.log10(elements)), Runtime.getRuntime().availableProcessors());
        }
        else {
            groupSize = this.maxGroupSize;
        }

        int step = elements / groupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < groupSize; ++group) {
            taskPool.submit(
                    new MatrixWithScalarTask(resultMatrix, matrix, scalar, genericEquation,
                            startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == groupSize - 2 ? elements : endIndex + step;
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

            var shape = result.shape();
            int row;
            int column;

            for (; startIndex < endIndex; ++startIndex) {
                column = (startIndex) % shape[1];
                row = (startIndex / shape[1]) % shape[0];

                result.set(row, column,
                        equation.operate(leftMatrix.get(row, column), rightMatrix.get(row, column)));
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

            var shape = result.shape();
            int row;
            int column;

            for (; startIndex < endIndex; ++startIndex) {
                column = (startIndex) % shape[1];
                row = (startIndex / shape[1]) % shape[0];

                result.set(row, column,
                        equation.operate(matrix.get(row, column), scalar));
            }
        }
    }
}
