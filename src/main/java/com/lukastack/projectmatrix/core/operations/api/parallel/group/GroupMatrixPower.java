package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Power;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperations;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupMatrixPower extends MatrixOperation implements Power {

    private final GroupMatrixOperations groupMatrixOperations;

    public GroupMatrixPower(Class<? extends Matrix> clazz,
                            final GroupMatrixOperations groupMatrixOperations) {

        super(clazz);

        this.groupMatrixOperations = groupMatrixOperations;
    }

    @Override
    public Matrix power(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        var shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, Math::pow);

        return result;
    }

    @Override
    public Matrix power(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(matrix, scalar, result, taskPool, Math::pow);

        return result;
    }
}
