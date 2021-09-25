package com.lukastack.projectmatrix.core.equations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialElementWiseMatrixEquation {

    default void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix resultMatrix, GenericEquation genericEquation) {

        int[] shape = leftMatrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                resultMatrix.set(row, col,
                        genericEquation.operate(leftMatrix.get(row, col), rightMatrix.get(row, col)));
            }
        }
    }

    default void operate(Matrix leftMatrix, double scalar, Matrix resultMatrix, GenericEquation genericEquation) {

        int[] shape = leftMatrix.shape();

        for (int row = 0; row < shape[0]; ++row) {
            for (int col = 0; col < shape[1]; ++col) {
                resultMatrix.set(row, col,
                        genericEquation.operate(leftMatrix.get(row, col), scalar));
            }
        }
    }
}
