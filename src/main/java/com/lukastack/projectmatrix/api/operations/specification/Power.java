package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Power {

    Matrix pow(Matrix leftMatrix, Matrix rightMatrix);

    Matrix pow(Matrix matrix, double scalar);
}
