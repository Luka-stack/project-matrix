package com.lukastack.projectmatrix.core.operations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface ISerialMatrixProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight);
}
