package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixPower;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixPowerImpl<E extends Matrix> extends MatrixOperationTemplate<E>
        implements IParallelMatrixPower {

    public IndividualMatrixPowerImpl(Class<E> clazz) {

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
