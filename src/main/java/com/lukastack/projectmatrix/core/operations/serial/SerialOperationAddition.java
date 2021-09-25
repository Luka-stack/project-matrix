package com.lukastack.projectmatrix.core.operations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialOperationAddition {

    Matrix add(Matrix leftMatrix, Matrix rightMatrix);

    Matrix add(Matrix matrix, double scalar);
}
