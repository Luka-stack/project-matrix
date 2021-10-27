package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface Root {

    Matrix root(Matrix leftMatrix, Matrix rightMatrix);

    Matrix root(Matrix matrix, double scalar);
}
