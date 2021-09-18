package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixRoot {

    Matrix root(Matrix leftMatrix, Matrix rightMatrix);

    Matrix root(Matrix matrix, double scalar);
}
