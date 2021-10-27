package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface Division {

    Matrix div(Matrix leftMatrix, Matrix rightMatrix);

    Matrix div(Matrix matrix, double scalar);
}
