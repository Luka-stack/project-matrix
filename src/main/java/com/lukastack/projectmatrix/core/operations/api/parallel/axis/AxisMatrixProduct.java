package com.lukastack.projectmatrix.core.operations.api.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixProductOperation;
import com.lukastack.projectmatrix.core.operations.api.parallel.MatrixOperation;
import com.lukastack.projectmatrix.core.operations.definitions.parallel.Product;

import java.util.concurrent.ThreadPoolExecutor;

public class AxisMatrixProduct extends MatrixOperation implements Product {

    private final AxisMatrixProductOperation axisMatrixProductOperation;

    public AxisMatrixProduct(Class<? extends Matrix> clazz,
                             final AxisMatrixProductOperation axisMatrixProductOperation) {

        super(clazz);

        this.axisMatrixProductOperation = axisMatrixProductOperation;
    }

    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        this.axisMatrixProductOperation.operate(matLeft, matRight, result, taskPool);

        return result;
    }
}
