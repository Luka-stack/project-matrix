package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialSubtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix);

    Matrix sub(Matrix matrix, double scalar);
}
