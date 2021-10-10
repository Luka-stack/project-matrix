package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualOperations implements IndividualMatrixOperations {

    @Override
    public void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                         final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        for (int row = 0; row < leftMatrix.shape()[0]; ++row) {

            for (int col = 0; col < leftMatrix.shape()[1]; ++col) {

                taskPool.submit(new IndividualMatrixOperationsTask(
                        resultMatrix, leftMatrix, rightMatrix, genericEquation, row, col)
                );
            }
        }
    }

    @Override
    public void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                         final ThreadPoolExecutor taskPool, final GenericEquation genericEquation) {

        for (int row = 0; row < matrix.shape()[0]; ++row) {

            for (int col = 0; col < matrix.shape()[1]; ++col) {

                taskPool.submit(new IndividualScalarOperationsTask(
                        resultMatrix, matrix, scalar, genericEquation, row, col)
                );

            }
        }
    }

    private static class IndividualMatrixOperationsTask implements Runnable {

        private final Matrix result;
        private final Matrix matLeft;
        private final Matrix matRight;

        private final GenericEquation equation;

        private final int row;
        private final int column;

        public IndividualMatrixOperationsTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                        GenericEquation equation, int row, int column) {

            this.row = row;
            this.column = column;

            this.result = result;
            this.matLeft = matLeft;
            this.matRight = matRight;

            this.equation = equation;
        }

        @Override
        public void run() {
            result.set(row, column, equation.operate(matLeft.get(row, column), matRight.get(row, column)));
        }
    }

    private static class IndividualScalarOperationsTask implements Runnable {

        private final Matrix result;
        private final Matrix matrix;
        private final double scalar;

        private final GenericEquation equation;

        private final int row;
        private final int column;

        public IndividualScalarOperationsTask(final Matrix result, final Matrix matrix, double scalar,
                                                    final GenericEquation equation, int row, int column) {

            this.row = row;
            this.column = column;

            this.result = result;
            this.matrix = matrix;
            this.scalar = scalar;

            this.equation = equation;
        }

        @Override
        public void run() {
            result.set(row, column, equation.operate(matrix.get(row, column), scalar));
        }
    }
}
