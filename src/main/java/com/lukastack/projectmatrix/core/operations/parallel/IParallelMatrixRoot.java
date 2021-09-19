package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface IParallelMatrixRoot {

    Matrix root(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix root(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
