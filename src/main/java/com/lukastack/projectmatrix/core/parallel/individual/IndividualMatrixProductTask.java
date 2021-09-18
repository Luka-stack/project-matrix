package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public class IndividualMatrixProductTask implements Runnable {

    private final Matrix result;
    private final Matrix matLeft;
    private final Matrix matRight;

    private final int row;
    private final int column;

    public IndividualMatrixProductTask(final Matrix result, final Matrix matLeft, final Matrix matRight,
                                       int row, int column) {

        this.row = row;
        this.column = column;

        this.result = result;
        this.matLeft = matLeft;
        this.matRight = matRight;
    }

    @Override
    public void run() {

        var colDim= matLeft.shape()[1];
        double movingResult = 0;

        for (int moving = 0; moving < colDim; ++moving) {
            movingResult += matLeft.get(row, moving) * matRight.get(moving, column);
        }

        result.set(row, column, movingResult);
    }
}
