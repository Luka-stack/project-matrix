package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Division;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupMatrixDivision extends MatrixOperation implements Division {

    private final GroupMatrixOperations groupMatrixOperations;

    public GroupMatrixDivision(Class<? extends Matrix> clazz,
                               final GroupMatrixOperations groupMatrixOperations) {

        super(clazz);

        this.groupMatrixOperations = groupMatrixOperations;
    }

    @Override
    public Matrix divide(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        var shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, (a, b) -> a / b);

        return result;
    }

    @Override
    public Matrix divide(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(matrix, scalar, result, taskPool, (a, b) -> a / b);

        return result;
    }
}
