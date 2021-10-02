package com.lukastack.projectmatrix.core.operations.api.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixOperations;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Addition;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisMatrixAddition extends MatrixOperation implements Addition {

    private final AxisMatrixOperations axisMatrixOperations;

    public AxisMatrixAddition(Class<? extends Matrix> clazz, final AxisMatrixOperations axisMatrixOperations) {

        super(clazz);

        this.axisMatrixOperations = axisMatrixOperations;
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        var shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.axisMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, Double::sum);

        return result;
    }

    @Override
    public Matrix add(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.axisMatrixOperations.operate(matrix, scalar, result, taskPool, Double::sum);

        return result;
    }
}
