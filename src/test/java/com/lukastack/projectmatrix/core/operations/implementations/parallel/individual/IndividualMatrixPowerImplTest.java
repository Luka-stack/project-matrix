package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class IndividualMatrixPowerImplTest {

    private final DecimalFormat toThreeDecimal = new DecimalFormat("0.000");
    private SingletonThreadPoolProvider poolProvider;
    private IndividualMatrixPower powerImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
        powerImpl = new IndividualMatrixPower(MatJv.class);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void power_Matrix_x_Matrix_correctEquations() throws ExecutionException, InterruptedException {

        Matrix matrixFirst = new MatJv(3, 3);
        Matrix matrixSecond = new MatJv(3, 3);

        matrixFirst.set(0, 0, 5.0);
        matrixFirst.set(0, 1, 8.0);
        matrixFirst.set(0, 2, 1.0);
        matrixFirst.set(1, 0, 7.0);
        matrixFirst.set(1, 1, 1.0);
        matrixFirst.set(1, 2, 5.0);
        matrixFirst.set(2, 0, 3.0);
        matrixFirst.set(2, 1, 2.0);
        matrixFirst.set(2, 2, 0.0);

        matrixSecond.set(0, 0, 7.0);
        matrixSecond.set(0, 1, 5.0);
        matrixSecond.set(0, 2, 1.0);
        matrixSecond.set(1, 0, 2.0);
        matrixSecond.set(1, 1, 4.0);
        matrixSecond.set(1, 2, 1.0);
        matrixSecond.set(2, 0, 1.0);
        matrixSecond.set(2, 1, 1.0);
        matrixSecond.set(2, 2, 1.0);

        var result = powerImpl.power(matrixFirst, matrixSecond, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(78125.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(32768.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(49.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(1.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(2.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void power_Matrix_x_Scalar_correctEquations() throws ExecutionException, InterruptedException {

        Matrix matrixFirst = new MatJv(3, 3);

        matrixFirst.set(0, 0, 3.0);
        matrixFirst.set(0, 1, 7.0);
        matrixFirst.set(0, 2, 2.0);
        matrixFirst.set(1, 0, 6.0);
        matrixFirst.set(1, 1, 2.0);
        matrixFirst.set(1, 2, 3.0);
        matrixFirst.set(2, 0, 1.0);
        matrixFirst.set(2, 1, 8.0);
        matrixFirst.set(2, 2, 3.0);

        var result = powerImpl.power(matrixFirst, 2, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(49.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(36.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(1.0, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(64.0, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {
        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = powerImpl.power(matrixFirst, 34, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertTrue(result instanceof MatJv);
    }
}