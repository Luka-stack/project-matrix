package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Power;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixOperations;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixPower extends MatrixOperation implements Power {

    private final IndividualMatrixOperations matrixOperations;

    public IndividualMatrixPower(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperations = new IndividualOperations();
    }

    public IndividualMatrixPower(Class<? extends Matrix> clazz, final IndividualMatrixOperations matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix power(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, Math::pow);

        return  result;
    }

    @Override
    public Matrix power(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();

        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, scalar, result, taskPool, Math::pow);

        return  result;
    }
}
