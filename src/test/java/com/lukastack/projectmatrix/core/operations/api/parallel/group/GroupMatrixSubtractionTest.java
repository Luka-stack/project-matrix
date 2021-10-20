package com.lukastack.projectmatrix.core.operations.api.parallel.group;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowOperation;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

class GroupMatrixSubtractionTest {

    private SingletonThreadPoolProvider poolProvider;
    private GroupMatrixSubtraction subtraction;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void add_Matrix_x_Matrix_WrongDimensions_ThrowsDimensionException() {

        subtraction = new GroupMatrixSubtraction(MatJv.class, new GroupRowOperation());
        var pool = poolProvider.provideThreadPool();

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> subtraction.sub(matrixFirst, matrixSecond, pool));
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {

        subtraction = new GroupMatrixSubtraction(MatJv.class, new GroupRowOperation());

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = subtraction.sub(matrixFirst, 34, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertTrue(result instanceof MatJv);
    }
}