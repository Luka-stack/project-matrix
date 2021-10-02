package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisColumnOperations implements AxisMatrixOperations {

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        for (int column = 0; column < leftMatrix.shape()[1]; ++column) {

            taskPool.submit(
                    new AxisColumnOperationsMatrixTask(resultMatrix, leftMatrix, rightMatrix, column, genericEquation)
            );
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        for (int column = 0; column < matrix.shape()[1]; ++column) {

            taskPool.submit(
                    new AxisColumnOperationsScalarTask(resultMatrix, matrix, scalar, column, genericEquation)
            );
        }

    }

    private static class AxisColumnOperationsMatrixTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final GenericEquation equation;

        private final int column;

        private AxisColumnOperationsMatrixTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                               int column, final GenericEquation equation) {

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;
            this.equation = equation;
            this.column = column;
        }

        @Override
        public void run() {

            for (int row = 0; row < matLeft.shape()[0]; ++row) {

                result.set(row, column,
                        equation.operate(matLeft.get(row, column), matRight.get(row, column))
                );
            }
        }
    }

    private static class AxisColumnOperationsScalarTask implements Runnable {

        private final Matrix result;
        private final Matrix matrix;
        private final double scalar;

        private final GenericEquation equation;

        private final int column;

        private AxisColumnOperationsScalarTask(final Matrix result, final Matrix matrix, double scalar,
                                               int column, final GenericEquation equation) {

            this.result = result;
            this.matrix = matrix;
            this.scalar = scalar;
            this.equation = equation;
            this.column = column;
        }

        @Override
        public void run() {

            for (int row = 0; row < matrix.shape()[0]; ++row) {

                result.set(row, column,
                        equation.operate(matrix.get(row, column), scalar)
                );
            }
        }
    }
}
