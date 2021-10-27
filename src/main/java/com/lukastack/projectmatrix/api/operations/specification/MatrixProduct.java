package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface MatrixProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight);
}
