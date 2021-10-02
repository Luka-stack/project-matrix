package com.lukastack.projectmatrix.core.operations.definitions.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Multiplication {

    Matrix multiply(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool);

    Matrix multiply(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool);
}
