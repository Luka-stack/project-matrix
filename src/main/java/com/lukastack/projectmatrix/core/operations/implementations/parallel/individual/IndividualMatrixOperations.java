package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface IndividualMatrixOperations {

    void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix resultMatrix,
                 final ThreadPoolExecutor taskPool, final GenericEquation genericEquation);

    void operate(final Matrix matrix, double scalar, final Matrix resultMatrix,
                 final ThreadPoolExecutor taskPool, final GenericEquation genericEquation);
}
