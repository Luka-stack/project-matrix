package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialMultiplication {

    Matrix multiply(Matrix leftMatrix, Matrix rightMatrix);

    Matrix multiply(Matrix matrix, double scalar);
}
