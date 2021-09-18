package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface IMatrixProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool);
}
