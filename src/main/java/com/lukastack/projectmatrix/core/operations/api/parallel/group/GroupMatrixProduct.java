package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.MatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProductOperation;
import com.lukastack.projectmatrix.errors.DimensionException;

import java.util.concurrent.ThreadPoolExecutor;

public class GroupMatrixProduct extends MatrixOperation implements MatrixProduct {

    private final GroupMatrixProductOperation groupMatrixProductOperation;

    public GroupMatrixProduct(Class<? extends Matrix> clazz,
                             final GroupMatrixProductOperation groupMatrixProductOperation) {

        super(clazz);

        this.groupMatrixProductOperation = groupMatrixProductOperation;
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(matLeft, matRight);

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        this.groupMatrixProductOperation.operate(matLeft, matRight, result, taskPool);

        return result;
    }

    @Override
    protected void assertCorrectDimension(Matrix leftMatrix, Matrix rightMatrix) {

        if (leftMatrix.shape()[1] != rightMatrix.shape()[0]) {
            throw new DimensionException(
                    String.format("Left side Matrix's column size and right side Matrix's row size must be equal %d != %d",
                            leftMatrix.shape()[1], rightMatrix.shape()[0]));
        }
    }
}
