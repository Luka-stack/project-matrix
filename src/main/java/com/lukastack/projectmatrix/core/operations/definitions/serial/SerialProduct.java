package com.lukastack.projectmatrix.core.operations.definitions.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight);
}
