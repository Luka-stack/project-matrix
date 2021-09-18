package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

// TODO Provide different interfaces for Serial and Parallel
public interface IMatrixProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool);
}
