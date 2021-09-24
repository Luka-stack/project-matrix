package com.lukastack.projectmatrix.core.parallel.individual;

import com.lukastack.projectmatrix.core.MatrixOperationTemplate;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.parallel.IParallelMatrixProduct;
import com.lukastack.projectmatrix.core.parallel.individual.tasks.IndividualMatrixProductTask;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public final class IndividualMatrixProductImpl<E extends Matrix> extends MatrixOperationTemplate<E>
        implements IParallelMatrixProduct {

    public IndividualMatrixProductImpl(Class<E> clazz) {

        super(clazz);
    }

    // TODO needs throw error if rows/cols not correct
    @Override
    public Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool) {

        int rowsLeft = matLeft.shape()[0];
        int columnsRight = matRight.shape()[1];

        Matrix result = createMatrix(rowsLeft, columnsRight);

        for (int rowId = 0; rowId < rowsLeft; ++rowId) {
            for (int colId = 0; colId < columnsRight; ++colId) {
                IndividualMatrixProductTask task = new IndividualMatrixProductTask(
                        result, matLeft, matRight, rowId, colId
                );

                taskPool.submit(task);
            }
        }
        return result;
    }
}
