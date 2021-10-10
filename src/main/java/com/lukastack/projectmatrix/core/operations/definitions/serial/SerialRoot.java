package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialRoot {

    Matrix root(Matrix leftMatrix, Matrix rightMatrix);

    Matrix root(Matrix matrix, double scalar);
}
