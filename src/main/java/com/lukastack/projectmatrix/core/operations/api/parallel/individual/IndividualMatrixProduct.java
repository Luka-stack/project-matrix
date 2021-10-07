package com.lukastack.projectmatrix.core.operations.api.parallel.individual;

import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Product;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixProductOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualProduct;

import java.util.concurrent.*;

public final class IndividualMatrixProduct extends MatrixOperation
        implements Product {

    private final IndividualMatrixProductOperation matrixOperation;

    public IndividualMatrixProduct(Class<? extends Matrix> clazz,
                                   final IndividualMatrixProductOperation matrixOperation) {

        super(clazz);

        this.matrixOperation = matrixOperation;
    }

    public IndividualMatrixProduct(Class<? extends Matrix> clazz) {

        super(clazz);

        this.matrixOperation = new IndividualProduct();
    }

    // TODO needs throw error if rows/cols not correct
    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        this.matrixOperation.operate(matLeft, matRight, result, taskPool);

        return result;
    }
}
