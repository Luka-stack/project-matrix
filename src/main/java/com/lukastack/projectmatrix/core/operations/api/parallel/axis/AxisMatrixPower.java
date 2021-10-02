package com.lukastack.projectmatrix.core.operations.api.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixOperations;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Power;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisMatrixPower extends MatrixOperation implements Power {

    private final AxisMatrixOperations axisMatrixOperations;

    public AxisMatrixPower(Class<? extends Matrix> clazz,  final AxisMatrixOperations axisMatrixOperations) {

        super(clazz);

        this.axisMatrixOperations = axisMatrixOperations;
    }

    @Override
    public Matrix power(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        var shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.axisMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, Math::pow);

        return result;
    }

    @Override
    public Matrix power(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.axisMatrixOperations.operate(matrix, scalar, result, taskPool, Math::pow);

        return result;
    }
}
