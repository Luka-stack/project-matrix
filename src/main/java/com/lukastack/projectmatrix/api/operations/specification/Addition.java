package com.lukastack.projectmatrix.api.operations.specification;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Addition {

    Matrix add(Matrix leftMatrix, Matrix rightMatrix);

    Matrix add(Matrix matrix, double scalar);
}
