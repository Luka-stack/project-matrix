package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixRoot;
import com.lukastack.projectmatrix.utils.NthRoot;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixRootImpl<E extends Matrix> extends MatrixOperationTemplate<E>
        implements IParallelMatrixRoot {

    public IndividualMatrixRootImpl(Class<E> clazz) {

        super(clazz);
    }

    @Override
    public Matrix root(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, taskPool, NthRoot::nthRoot);

        return  result;
    }

    @Override
    public Matrix root(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, scalar, result, taskPool, NthRoot::nthRoot);

        return  result;
    }
}
