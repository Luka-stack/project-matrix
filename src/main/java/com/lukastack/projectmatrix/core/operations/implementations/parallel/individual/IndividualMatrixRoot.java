package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.operations.implementations.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.OperationRoot;
import com.lukastack.projectmatrix.utils.NthRoot;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixRoot extends MatrixOperation
        implements OperationRoot {

    public IndividualMatrixRoot(Class<? extends Matrix> clazz) {

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
