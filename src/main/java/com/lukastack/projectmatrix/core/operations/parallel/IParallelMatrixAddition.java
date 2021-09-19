package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface IParallelMatrixAddition {

    Matrix add(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix add(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
