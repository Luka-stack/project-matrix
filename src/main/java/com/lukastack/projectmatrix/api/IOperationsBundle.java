package com.lukastack.projectmatrix.api;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface IOperationsBundle {

    Matrix matMul(Matrix matOne, Matrix matTwo);
}
