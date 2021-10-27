package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface Power {

    Matrix pow(Matrix leftMatrix, Matrix rightMatrix);

    Matrix pow(Matrix matrix, double scalar);
}
