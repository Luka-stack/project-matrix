package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface IParallelMatrixPower {

    Matrix power(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix power(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
