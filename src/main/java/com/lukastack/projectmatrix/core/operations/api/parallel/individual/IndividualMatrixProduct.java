package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.MatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultMatrixProduct;
import com.lukastack.projectmatrix.errors.DimensionException;

import java.util.concurrent.*;

public final class IndividualMatrixProduct extends MatrixOperation
        implements MatrixProduct {

    private final com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixProduct matrixOperation;

    public IndividualMatrixProduct(Class<? extends Matrix> clazz,
                                   final com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixProduct matrixOperation) {

        super(clazz);

        this.matrixOperation = matrixOperation;
    }

    public IndividualMatrixProduct(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperation = new IndividualDefaultMatrixProduct();
    }

    // TODO needs throw error if rows/cols not correct
    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        this.assertCorrectDimension(matLeft, matRight);

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        this.matrixOperation.operate(matLeft, matRight, result, taskPool);

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
