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

class GroupMatrixDivisionTest {

    private SingletonThreadPoolProvider poolProvider;
    private GroupMatrixDivision division;

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

        division = new GroupMatrixDivision(MatJv.class, new GroupRowOperation());
        var pool = poolProvider.provideThreadPool();

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> division.divide(matrixFirst, matrixSecond, pool));
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {

        division = new GroupMatrixDivision(MatJv.class, new GroupRowOperation());

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = division.divide(matrixFirst, 34, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertTrue(result instanceof MatJv);
    }
}