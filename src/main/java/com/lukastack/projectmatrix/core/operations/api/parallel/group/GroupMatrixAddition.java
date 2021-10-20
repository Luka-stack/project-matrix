package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Addition;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperation;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupMatrixAddition extends MatrixOperation implements Addition {

    private final GroupMatrixOperation groupMatrixOperations;

    public GroupMatrixAddition(Class<? extends Matrix> clazz,
                                  final GroupMatrixOperation groupMatrixOperations) {
        super(clazz);

        this.groupMatrixOperations = groupMatrixOperations;
    }

    @Override
    public Matrix add(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        var shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, Double::sum);

        return result;
    }

    @Override
    public Matrix add(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(matrix, scalar, result, taskPool, Double::sum);

        return result;
    }
}
