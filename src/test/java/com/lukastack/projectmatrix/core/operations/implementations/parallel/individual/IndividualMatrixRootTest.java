package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.individual.IndividualMatrixRoot;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class IndividualMatrixRootTest {

    private final DecimalFormat toThreeDecimal = new DecimalFormat("0.0");
    private SingletonThreadPoolProvider poolProvider;
    private IndividualMatrixRoot rootImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
        rootImpl = new IndividualMatrixRoot(MatJv.class);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void root_Matrix_x_Matrix_correctEquations() throws ExecutionException, InterruptedException {

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(2, 2);

        matrixFirst.set(0, 0, 4.0);
        matrixFirst.set(0, 1, 9.0);
        matrixFirst.set(1, 0, 27.0);
        matrixFirst.set(1, 1, 125.0);

        matrixSecond.set(0, 0, 2.0);
        matrixSecond.set(0, 1, 2.0);
        matrixSecond.set(1, 0, 3.0);
        matrixSecond.set(1, 1, 3.0);

        var result = rootImpl.root(matrixFirst, matrixSecond, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(2.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(3.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(3.00, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(5.00, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
    }

    @Test
    void root_Matrix_x_Matrix_WrongDimensions_ThrowsDimensionException() {

        var pool = poolProvider.provideThreadPool();

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> rootImpl.root(matrixFirst, matrixSecond, pool));
    }

    @Test
    void root_Matrix_x_Scalar_correctEquations() throws ExecutionException, InterruptedException {

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 49.0);
        matrixFirst.set(0, 1, 100.0);
        matrixFirst.set(1, 0, 81.0);
        matrixFirst.set(1, 1, 144.0);

        var result = rootImpl.root(matrixFirst, 2, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(7.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(10.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(12.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {
        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = rootImpl.root(matrixFirst, 34, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertTrue(result instanceof MatJv);
    }
}