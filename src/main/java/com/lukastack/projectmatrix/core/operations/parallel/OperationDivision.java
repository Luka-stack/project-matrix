package com.lukastack.projectmatrix.core.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface OperationDivision {

    Matrix divide(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix divide(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
