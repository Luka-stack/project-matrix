package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public class SerialProductOperation implements SerialMatrixProductOperation {

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix result) {

        int columnsRight = rightMatrix.shape()[1];

        for (int leftRow = 0; leftRow < leftMatrix.shape()[0]; ++leftRow) {
            for (int rightCol = 0; rightCol < columnsRight; ++rightCol) {

                for (int leftCol = 0; leftCol < leftMatrix.shape()[1]; ++leftCol) {
                    result.set(leftRow, rightCol,
                            result.get(leftRow, rightCol) +
                                    leftMatrix.get(leftRow, leftCol) * rightMatrix.get(leftCol, rightCol));
                }
            }
        }
    }
}
