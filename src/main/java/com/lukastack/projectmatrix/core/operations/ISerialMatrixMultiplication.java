package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixMultiplication {

    Matrix multiply(Matrix leftMatrix, Matrix rightMatrix);

    Matrix multiply(Matrix matrix, double scalar);
}