package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.operations.implementations.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.OperationMultiplication;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixMultiplication extends MatrixOperation
        implements OperationMultiplication {

    public IndividualMatrixMultiplication(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    @Override
    public Matrix multiply(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a * b);

        return  result;
    }

    @Override
    public Matrix multiply(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, scalar, result, taskPool, (a, b) -> a * b);

        return  result;
    }
}
