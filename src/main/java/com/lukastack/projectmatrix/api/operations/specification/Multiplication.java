package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface Multiplication {

    Matrix mul(Matrix leftMatrix, Matrix rightMatrix);

    Matrix mul(Matrix leftMatrix, double scalar);
}
