package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Multiplication;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class IndividualMatrixMultiplication extends MatrixOperation
        implements Multiplication {

    private final IndividualMatrixOperation matrixOperations;

    public IndividualMatrixMultiplication(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperations = new IndividualDefaultOperation();
    }

    public IndividualMatrixMultiplication(Class<? extends Matrix> clazz, final IndividualMatrixOperation matrixOperations) {

        super(clazz);

        this.matrixOperations = matrixOperations;
    }

    @Override
    public Matrix multiply(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a * b);

        return  result;
    }

    @Override
    public Matrix multiply(Matrix leftMatrix, double scalar, ThreadPoolExecutor taskPool) {

        int[] shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.matrixOperations.operate(leftMatrix, scalar, result, taskPool, (a, b) -> a * b);

        return  result;
    }
}
