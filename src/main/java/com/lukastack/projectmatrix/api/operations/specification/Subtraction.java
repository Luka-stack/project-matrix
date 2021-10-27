package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Subtraction {

    Matrix sub(Matrix leftMatrix, Matrix rightMatrix);

    Matrix sub(Matrix matrix, double scalar);
}
