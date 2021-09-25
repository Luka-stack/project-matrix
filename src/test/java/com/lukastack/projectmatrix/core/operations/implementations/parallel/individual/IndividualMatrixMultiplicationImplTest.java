package com.lukastack.projectmatrix.core.operations.implementations.parallel.individual;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class IndividualMatrixMultiplicationImplTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private ThreadPoolProvider poolProvider;
    private IndividualMatrixMultiplication multiplyImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
        multiplyImpl = new IndividualMatrixMultiplication(MatJv.class);
    }

    @Test
    void multiply_Matrix_x_Matrix_correctEquations() throws ExecutionException, InterruptedException {

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

        var pool = poolProvider.provideThreadPool();
        var result = multiplyImpl.multiply(matrixFirst, matrixSecond, pool);

        poolProvider.waitForCompletion();
        poolProvider.close();

        Assertions.assertEquals(35.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(40.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(14.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void multiply_Matrix_x_Scalar_correctEquations() throws ExecutionException, InterruptedException {

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

        var pool = poolProvider.provideThreadPool();
        var result = multiplyImpl.multiply(matrixFirst, 9, pool);

        poolProvider.waitForCompletion();
        poolProvider.close();

        Assertions.assertEquals(27.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(63.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(18.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(54.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(18.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(27.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(72.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(27.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {
        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var pool = poolProvider.provideThreadPool();
        var result = multiplyImpl.multiply(matrixFirst, 34, pool);

        poolProvider.waitForCompletion();
        poolProvider.close();

        Assertions.assertTrue(result instanceof MatJv);
    }
}