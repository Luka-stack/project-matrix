package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.Matrix;

public interface SerialMatrixOperation {

    void operate(Matrix leftMatrix, Matrix rightMatrix, Matrix resultMatrix, GenericEquation genericEquation);

    void operate(Matrix matrix, double scalar, Matrix resultMatrix, GenericEquation genericEquation);
}
