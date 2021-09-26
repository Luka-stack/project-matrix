package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface OperationMultiplication {

    Matrix multiply(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix multiply(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
