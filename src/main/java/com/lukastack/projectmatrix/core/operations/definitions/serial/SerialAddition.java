package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialAddition {

    Matrix add(Matrix leftMatrix, Matrix rightMatrix);

    Matrix add(Matrix matrix, double scalar);
}
