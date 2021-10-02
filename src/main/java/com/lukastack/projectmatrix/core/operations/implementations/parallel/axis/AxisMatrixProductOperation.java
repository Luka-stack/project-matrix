package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface AxisMatrixProductOperation {

    void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix result,
                 final ThreadPoolExecutor taskPool);
}
