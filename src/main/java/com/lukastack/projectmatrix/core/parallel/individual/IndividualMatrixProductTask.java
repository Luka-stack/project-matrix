package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.matrices.MatJv;

public class IndividualMatrixProductTask implements Runnable {

    private final MatJv result;
    private final MatJv matLeft;
    private final MatJv matRight;

    private final int row;
    private final int column;

    public IndividualMatrixProductTask(final MatJv result, final MatJv matLeft, final MatJv matRight,
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
