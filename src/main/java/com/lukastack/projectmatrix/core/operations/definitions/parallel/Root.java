package com.lukastack.projectmatrix.core.operations.definitions.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Root {

    Matrix root(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix root(Matrix matrix, double scalar, ThreadPoolExecutor taskPool);
}
