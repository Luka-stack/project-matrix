package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialDivision {

    Matrix divide(Matrix leftMatrix, Matrix rightMatrix);

    Matrix divide(Matrix matrix, double scalar);
}
