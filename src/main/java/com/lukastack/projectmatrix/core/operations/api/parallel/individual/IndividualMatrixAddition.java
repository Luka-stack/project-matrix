package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Addition;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixAddition extends MatrixOperation implements Addition {

    private final IndividualMatrixOperation matrixOperations;

    public IndividualMatrixAddition(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperations = new IndividualDefaultOperation();
    }

    public IndividualMatrixAddition(Class<? extends Matrix> clazz,
                                    final IndividualMatrixOperation matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, Double::sum);

        return result;
    }

    @Override
    public Matrix add(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, scalar, result, taskPool, Double::sum);

        return result;
    }
}
