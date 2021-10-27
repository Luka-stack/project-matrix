package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface Subtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix);

    Matrix sub(Matrix matrix, double scalar);
}
