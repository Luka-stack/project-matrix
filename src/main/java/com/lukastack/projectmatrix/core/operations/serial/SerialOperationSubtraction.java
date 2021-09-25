package com.lukastack.projectmatrix.core.operations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialOperationSubtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix);

    Matrix sub(Matrix matrix, double scalar);
}