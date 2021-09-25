package com.lukastack.projectmatrix.core.operations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialOperationRoot {

    Matrix root(Matrix leftMatrix, Matrix rightMatrix);

    Matrix root(Matrix matrix, double scalar);
}
