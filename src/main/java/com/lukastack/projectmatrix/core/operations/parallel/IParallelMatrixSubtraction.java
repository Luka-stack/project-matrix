package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface IParallelMatrixSubtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix sub(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
