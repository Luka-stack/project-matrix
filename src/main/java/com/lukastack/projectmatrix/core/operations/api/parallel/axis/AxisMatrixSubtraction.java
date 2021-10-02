package com.lukastack.projectmatrix.core.operations.api.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixOperations;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Subtraction;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisMatrixSubtraction extends MatrixOperation implements Subtraction {

    private final AxisMatrixOperations axisMatrixOperations;

    public AxisMatrixSubtraction(Class<? extends Matrix> clazz,  final AxisMatrixOperations axisMatrixOperations) {

        super(clazz);

        this.axisMatrixOperations = axisMatrixOperations;
    }
    @Override
    public Matrix sub(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        var shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.axisMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a - b);

        return result;
    }

    @Override
    public Matrix sub(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.axisMatrixOperations.operate(matrix, scalar, result, taskPool, (a, b) -> a - b);

        return result;
    }
}
