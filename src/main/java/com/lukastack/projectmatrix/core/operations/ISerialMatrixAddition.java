package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixAddition {

    Matrix add(Matrix leftMatrix, Matrix rightMatrix);

    Matrix add(Matrix matrix, double scalar);
}