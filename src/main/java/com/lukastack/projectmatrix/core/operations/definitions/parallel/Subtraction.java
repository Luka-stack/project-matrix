package com.lukastack.projectmatrix.core.operations.definitions.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Subtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix sub(Matrix matrix, double scalar, ThreadPoolExecutor taskPool);
}
