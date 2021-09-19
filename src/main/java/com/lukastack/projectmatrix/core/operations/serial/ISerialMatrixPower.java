package com.lukastack.projectmatrix.core.operations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixPower {

    Matrix power(Matrix leftMatrix, Matrix rightMatrix);

    Matrix power(Matrix matrix, double scalar);
}
