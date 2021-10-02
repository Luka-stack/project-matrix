package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialPower {

    Matrix power(Matrix leftMatrix, Matrix rightMatrix);

    Matrix power(Matrix matrix, double scalar);
}
