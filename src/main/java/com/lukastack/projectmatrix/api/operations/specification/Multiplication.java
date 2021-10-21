package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Multiplication {

    Matrix mul(Matrix leftMatrix, Matrix rightMatrix);

    Matrix mul(Matrix leftMatrix, double scalar);
}
