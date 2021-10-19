package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisRowOperations implements AxisMatrixOperations {

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        for (int row = 0; row < leftMatrix.shape()[0]; ++row) {

            taskPool.submit(
                    new AxisRowOperationsMatrixTask(resultMatrix, leftMatrix, rightMatrix, row, genericEquation)
            );
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                        final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        for (int row = 0; row < matrix.shape()[0]; ++row) {

            taskPool.submit(
                    new AxisRowOperationsScalarTask(resultMatrix, matrix, scalar, row, genericEquation)
            );
        }
    }

    private static class AxisRowOperationsMatrixTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final GenericEquation equation;

        private final int row;

        private AxisRowOperationsMatrixTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                            int row, final GenericEquation equation) {

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;
            this.equation = equation;
            this.row = row;
        }

        @Override
        public void run() {

            for (int column = 0; column < matLeft.shape()[1]; ++column) {

                result.set(row, column,
                        equation.operate(matLeft.get(row, column), matRight.get(row, column))
                );
            }
        }
    }

    private static class AxisRowOperationsScalarTask implements Runnable {

        private final Matrix result;
        private final Matrix matrix;
        private final double scalar;

        private final GenericEquation equation;

        private final int row;

        private AxisRowOperationsScalarTask(final Matrix result, final Matrix matrix, double scalar, int row,
                                            final GenericEquation equation) {

            this.result = result;
            this.matrix = matrix;
            this.scalar = scalar;
            this.equation = equation;
            this.row = row;
        }

        @Override
        public void run() {

            for (int column = 0; column < matrix.shape()[1]; ++column) {

                result.set(row, column,
                        equation.operate(matrix.get(row, column), scalar)
                );
            }
        }
    }
}
