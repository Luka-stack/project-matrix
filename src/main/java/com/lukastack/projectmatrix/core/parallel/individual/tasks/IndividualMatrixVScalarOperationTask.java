package com.lukastack.projectmatrix.core.parallel.individual.tasks;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.GenericEquation;

public class IndividualMatrixVScalarOperationTask implements Runnable {

    private final Matrix result;
    private final Matrix matrix;
    private final double scalar;

    private final GenericEquation equation;

    private final int row;
    private final int column;

    public IndividualMatrixVScalarOperationTask(final Matrix result, final Matrix matrix, double scalar,
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
