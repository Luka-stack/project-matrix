package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixAddition;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixAdditionImpl<E extends Matrix> extends MatrixOperationTemplate<E>
        implements IParallelMatrixAddition {

    public IndividualMatrixAdditionImpl(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, taskPool, Double::sum);

        return  result;
    }

    @Override
    public Matrix add(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, scalar, result, taskPool, Double::sum);

        return  result;
    }
}
