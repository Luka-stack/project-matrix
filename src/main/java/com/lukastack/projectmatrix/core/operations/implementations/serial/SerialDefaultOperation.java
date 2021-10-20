package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;

public class SerialDefaultOperation implements SerialMatrixOperation {

    @Override
    public void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix resultMatrix, GenericEquation genericEquation) {

        int[] shape = leftMatrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                resultMatrix.set(row, col,
                        genericEquation.operate(leftMatrix.get(row, col), rightMatrix.get(row, col)));
            }
        }
    }

    @Override
    public void operate(Matrix matrix, double scalar, Matrix resultMatrix, GenericEquation genericEquation) {

        int[] shape = matrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                resultMatrix.set(row, col,
                        genericEquation.operate(matrix.get(row, col), scalar));
            }
        }
    }
}
