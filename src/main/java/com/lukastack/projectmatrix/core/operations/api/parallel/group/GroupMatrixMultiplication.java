package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Multiplication;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupMatrixMultiplication extends MatrixOperation implements Multiplication {

    private final GroupMatrixOperation groupMatrixOperations;

    public GroupMatrixMultiplication(Class<? extends Matrix> clazz,
                                     final GroupMatrixOperation groupMatrixOperations) {

        super(clazz);

        this.groupMatrixOperations = groupMatrixOperations;
    }

    @Override
    public Matrix multiply(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        var shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a * b);

        return result;
    }

    @Override
    public Matrix multiply(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(matrix, scalar, result, taskPool, (a, b) -> a * b);

        return result;
    }
}
