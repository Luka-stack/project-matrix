package com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupRowOperations implements GroupMatrixOperations {

    private int maxGroupSize;

    public GroupRowOperations() {

        maxGroupSize = -1;
    }

    public GroupRowOperations(int maxGroupSize) {

        this.maxGroupSize = maxGroupSize;
    }

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        int rows = leftMatrix.shape()[0];
        int groupSize;

        if (this.maxGroupSize == -1) {
            groupSize = (int) Math.pow(Math.E, Math.log10(rows));
        }
        else {
            groupSize = this.maxGroupSize;
        }

        int step = rows / groupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < groupSize; ++group) {
            taskPool.submit(
                    new MatrixWithMatrixTask(resultMatrix, leftMatrix, rightMatrix, genericEquation,
                            startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == groupSize - 2 ? rows : endIndex + step;
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        int rows = matrix.shape()[0];
        int groupSize;

        if (this.maxGroupSize == -1) {
            groupSize = (int) Math.pow(Math.E, Math.log10(rows));
        }
        else {
            groupSize = this.maxGroupSize;
        }

        int step = rows / groupSize;
        int startIndex = 0;
        int endIndex = step;

        for (int group = 0; group < groupSize; ++group) {
            taskPool.submit(
                    new MatrixWithScalarTask(resultMatrix, matrix, scalar, genericEquation,
                            startIndex, endIndex)
            );
            startIndex = endIndex;
            endIndex = group == groupSize - 2 ? rows : endIndex + step;
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

                for (int column = 0; column < leftMatrix.shape()[1]; ++column) {
                    result.set(startIndex, column,
                            equation.operate(leftMatrix.get(startIndex, column), rightMatrix.get(startIndex, column)));
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

                for (int column = 0; column < matrix.shape()[1]; ++column) {
                    result.set(startIndex, column,
                            equation.operate(matrix.get(startIndex, column), scalar));
                }
            }
        }
    }

}
