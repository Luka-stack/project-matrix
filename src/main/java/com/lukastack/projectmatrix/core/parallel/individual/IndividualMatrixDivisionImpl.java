package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixDivision;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixDivisionImpl<E extends Matrix> extends MatrixOperationTemplate<E>
        implements IParallelMatrixDivision {

    public IndividualMatrixDivisionImpl(Class<E> clazz) {

        super(clazz);
    }

    @Override
    public Matrix divide(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a / b);

        return  result;
    }

    @Override
    public Matrix divide(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.operate(leftMatrix, scalar, result, taskPool, (a, b) -> a / b);

        return  result;
    }
}
