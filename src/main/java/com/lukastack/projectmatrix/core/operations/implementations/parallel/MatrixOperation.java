package com.lukastack.projectmatrix.core.operations.implementations.parallel;

import com.lukastack.projectmatrix.core.equations.ElementWiseMatrixEquation;
import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.Matrix;

public abstract class MatrixOperation implements ElementWiseMatrixEquation, CreateMatrix {

    protected final Class<? extends Matrix> clazz;

    protected MatrixOperation(Class<? extends Matrix> clazz) {
        this.clazz = clazz;
    }

    protected Matrix createMatrix(int rows, int cols) {

        return this.createMatrix(clazz, rows, cols);
    }
}
