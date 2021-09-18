package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixSubtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix);

    Matrix sub(Matrix matrix, double scalar);
}
