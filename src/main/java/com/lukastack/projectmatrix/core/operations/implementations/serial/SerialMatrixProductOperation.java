package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface SerialMatrixProductOperation {

    void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix result);
}
