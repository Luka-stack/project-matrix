package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.operations.implementations.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.OperationPower;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixPower extends MatrixOperation
        implements OperationPower {

    public IndividualMatrixPower(Class<? extends Matrix> clazz) {

        super(clazz);
    }

    @Override
    public Matrix power(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, taskPool, Math::pow);

        return  result;
    }

    @Override
    public Matrix power(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, scalar, result, taskPool, Math::pow);

        return  result;
    }
}
