package com.lukastack.projectmatrix.core.operations.api.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixProductOperation;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.MatrixProduct;
import com.lukastack.projectmatrix.errors.DimensionException;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisMatrixProduct extends MatrixOperation implements MatrixProduct {

    private final AxisMatrixProductOperation axisMatrixProductOperation;

    public AxisMatrixProduct(Class<? extends Matrix> clazz,
                             final AxisMatrixProductOperation axisMatrixProductOperation) {

        super(clazz);

        this.axisMatrixProductOperation = axisMatrixProductOperation;
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(matLeft, matRight);

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        this.axisMatrixProductOperation.operate(matLeft, matRight, result, taskPool);

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
