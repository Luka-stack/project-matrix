package com.lukastack.projectmatrix.core.operations.definitions.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Power {

    Matrix power(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix power(Matrix matrix, double scalar, ThreadPoolExecutor taskPool);
}
