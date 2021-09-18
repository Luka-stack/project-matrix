package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixDivision {

    Matrix divide(Matrix leftMatrix, Matrix rightMatrix);

    Matrix divide(Matrix matrix, double scalar);
}
