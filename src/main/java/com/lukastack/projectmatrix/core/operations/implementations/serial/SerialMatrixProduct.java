package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialMatrixProduct {

    void operate(final Matrix leftMatrix, final Matrix rightMatrix, final Matrix result);
}
