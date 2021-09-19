package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.serial.GenericEquation;

public class IndividualMatrixOperationTask implements Runnable {

    private final Matrix result;
    private final Matrix matLeft;
    private final Matrix matRight;

    private final GenericEquation equation;

    private final int row;
    private final int column;

    public IndividualMatrixOperationTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                         GenericEquation equation,
                                         int row, int column) {

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
