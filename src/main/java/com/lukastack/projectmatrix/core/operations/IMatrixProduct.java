package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface IMatrixProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight);
}
