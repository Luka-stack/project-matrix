package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.diagonal;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisDiagonalOperations implements AxisMatrixOperations {

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        var shape = resultMatrix.shape();

        for (int column = 0; column < shape[1]; ++column) {
            taskPool.submit(new AxisDiagonalOperationsMatrixTask(resultMatrix, leftMatrix, rightMatrix,
                    genericEquation, 0, column));
        }

        for (int row = 1; row < shape[0]; ++row) {
            taskPool.submit(new AxisDiagonalOperationsMatrixTask(resultMatrix, leftMatrix, rightMatrix,
                    genericEquation, row, 0));
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        var shape = resultMatrix.shape();

        for (int column = 0; column < shape[1]; ++column) {
            taskPool.submit(new AxisDiagonalOperationsScalarTask(resultMatrix, matrix, scalar,
                    genericEquation, 0, column));
        }

        for (int row = 1; row < shape[0]; ++row) {
            taskPool.submit(new AxisDiagonalOperationsScalarTask(resultMatrix, matrix, scalar,
                    genericEquation, row, 0));
        }
    }

    private static class AxisDiagonalOperationsMatrixTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final GenericEquation equation;

        private int row;
        private int column;

        public AxisDiagonalOperationsMatrixTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                                final GenericEquation equation, int row, int column) {

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;
            this.equation = equation;
            this.row = row;
            this.column = column;
        }

        @Override
        public void run() {

            var resultShape = result.shape();

            for (; row < resultShape[0] && column < resultShape[1]; ++row, ++column) {

                result.set(row, column,
                        equation.operate(matLeft.get(row, column), matRight.get(row, column)));
            }
        }
    }

    private static class AxisDiagonalOperationsScalarTask implements Runnable {

        private final Matrix result;
        private final Matrix matrix;
        private final double scalar;

        private final GenericEquation equation;

        private int row;
        private int column;

        public AxisDiagonalOperationsScalarTask(final Matrix result, final Matrix matrix, double scalar,
                                                final GenericEquation equation, int row, int column) {

            this.result = result;
            this.matrix = matrix;
            this.scalar = scalar;
            this.equation = equation;
            this.row = row;
            this.column = column;
        }

        @Override
        public void run() {

            var resultShape = result.shape();

            for (; row < resultShape[0] && column < resultShape[1]; ++row, ++column) {

                result.set(row, column,
                        equation.operate(matrix.get(row, column), scalar));
            }
        }
    }
}
