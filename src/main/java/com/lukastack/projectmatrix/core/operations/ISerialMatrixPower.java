package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixPower {

    Matrix power(Matrix leftMatrix, Matrix rightMatrix);

    Matrix power(Matrix matrix, double scalar);
}
