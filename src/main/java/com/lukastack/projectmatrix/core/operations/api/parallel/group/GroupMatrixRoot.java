package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Root;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperation;
import com.lukastack.projectmatrix.utils.NthRoot;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupMatrixRoot extends MatrixOperation implements Root {

    private final GroupMatrixOperation groupMatrixOperations;

    public GroupMatrixRoot(Class<? extends Matrix> clazz,
                          final GroupMatrixOperation groupMatrixOperations) {

        super(clazz);

        this.groupMatrixOperations = groupMatrixOperations;
    }

    @Override
    public Matrix root(Matrix leftMatrix, Matrix rightMatrix, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(leftMatrix, rightMatrix);

        var shape = leftMatrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(leftMatrix, rightMatrix, result, taskPool, NthRoot::nthRoot);

        return result;
    }

    @Override
    public Matrix root(Matrix matrix, double scalar, ThreadPoolExecutor taskPool) {

        var shape = matrix.shape();
        Matrix result = createMatrix(shape[0], shape[1]);

        this.groupMatrixOperations.operate(matrix, scalar, result, taskPool, NthRoot::nthRoot);

        return result;
    }
}
