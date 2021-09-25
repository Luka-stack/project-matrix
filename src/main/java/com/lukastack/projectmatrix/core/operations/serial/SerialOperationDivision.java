package com.lukastack.projectmatrix.core.operations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialOperationDivision {

    Matrix divide(Matrix leftMatrix, Matrix rightMatrix);

    Matrix divide(Matrix matrix, double scalar);
}
