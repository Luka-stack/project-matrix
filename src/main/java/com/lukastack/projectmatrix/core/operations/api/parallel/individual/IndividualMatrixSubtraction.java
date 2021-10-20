package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Subtraction;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixSubtraction extends MatrixOperation
        implements Subtraction {

    private final IndividualMatrixOperation matrixOperations;

    public IndividualMatrixSubtraction(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperations = new IndividualDefaultOperation();
    }

    public IndividualMatrixSubtraction(Class<? extends Matrix> clazz, final IndividualMatrixOperation matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix sub(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a - b);

        return  result;
    }

    @Override
    public Matrix sub(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, scalar, result, taskPool, (a, b) -> a - b);

        return  result;
    }
}
