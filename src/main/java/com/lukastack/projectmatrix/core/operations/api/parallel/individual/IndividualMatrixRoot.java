package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Root;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixOperations;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualOperations;
import com.lukastack.projectmatrix.utils.NthRoot;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixRoot extends MatrixOperation
        implements Root {

    private final IndividualMatrixOperations matrixOperations;

    public IndividualMatrixRoot(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperations = new IndividualOperations();
    }

    public IndividualMatrixRoot(Class<? extends Matrix> clazz, final IndividualMatrixOperations matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix root(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, NthRoot::nthRoot);

        return  result;
    }

    @Override
    public Matrix root(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, scalar, result, taskPool, NthRoot::nthRoot);

        return  result;
    }
}
