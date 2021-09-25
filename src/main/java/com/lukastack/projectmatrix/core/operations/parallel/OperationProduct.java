package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface OperationProduct {

    Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool);
}
