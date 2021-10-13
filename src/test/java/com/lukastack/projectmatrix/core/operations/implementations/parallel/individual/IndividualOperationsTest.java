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

class IndividualOperationsTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private SingletonThreadPoolProvider poolProvider;
    private IndividualOperations operations;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void operate_correctEquation_Matrix_v_Matrix_squareMatrix() throws ExecutionException, InterruptedException {

        operations = new IndividualOperations();

        correctEquation_Matrix_v_Matrix_squareMatrix_test();
    }

    @Test
    void operate_correctEquations_Matrix_v_Matrix_longerRow() throws ExecutionException, InterruptedException {

        operations = new IndividualOperations();
        correctEquation_Matrix_v_Matrix_longerRow_test();
    }

    @Test
    void operate_correctEquations_Matrix_v_Matrix_longerColumn() throws ExecutionException, InterruptedException {

        operations = new IndividualOperations();
        correctEquation_Matrix_v_Matrix_longerColumn_test();
    }

    @Test
    void operate_correctEquation_Matrix_v_Scalar_squareMatrix() throws ExecutionException, InterruptedException {

        operations = new IndividualOperations();

        correctEquation_Matrix_v_Scalar_squareMatrix_test();
    }

    @Test
    void operate_correctEquations_Matrix_v_Scalar_longerRow() throws ExecutionException, InterruptedException {

        operations = new IndividualOperations();
        correctEquation_Matrix_v_Scalar_longerRow_test();
    }

    @Test
    void operate_correctEquations_Matrix_v_Scalar_longerColumn() throws ExecutionException, InterruptedException {

        operations = new IndividualOperations();
        correctEquation_Matrix_v_Scalar_longerColumn_test();
    }

    private void correctEquation_Matrix_v_Matrix_longerRow_test() throws ExecutionException, InterruptedException {

        Matrix matrixFirst = new MatJv(5, 2);
        Matrix matrixSecond = new MatJv(5, 2);
        Matrix result = new MatJv(5, 2);

        matrixFirst.set(0, 0, 5.0);
        matrixFirst.set(0, 1, 8.0);
        matrixFirst.set(1, 0, 1.0);
        matrixFirst.set(1, 1, 7.0);
        matrixFirst.set(2, 0, 1.0);
        matrixFirst.set(2, 1, 5.0);
        matrixFirst.set(3, 0, 3.0);
        matrixFirst.set(3, 1, 2.0);
        matrixFirst.set(4, 0, 0.0);
        matrixFirst.set(4, 1, 0.0);

        matrixSecond.set(0, 0, 7.0);
        matrixSecond.set(0, 1, 5.0);
        matrixSecond.set(1, 0, 1.0);
        matrixSecond.set(1, 1, 2.0);
        matrixSecond.set(2, 0, 4.0);
        matrixSecond.set(2, 1, 1.0);
        matrixSecond.set(3, 0, 1.0);
        matrixSecond.set(3, 1, 1.0);
        matrixSecond.set(4, 0, 1.0);
        matrixSecond.set(4, 1, 1.0);

        operations.operate(matrixFirst, matrixSecond, result, poolProvider.provideThreadPool(), Double::sum);
        poolProvider.waitForCompletion();

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(13.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(3, 0))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(3, 1))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(4, 0))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(4, 1))));
    }

    private void correctEquation_Matrix_v_Matrix_longerColumn_test() throws ExecutionException, InterruptedException {

        Matrix matrixFirst = new MatJv(2, 5);
        Matrix matrixSecond = new MatJv(2, 5);
        Matrix result = new MatJv(2, 5);

        matrixFirst.set(0, 0, 5.0);
        matrixFirst.set(0, 1, 8.0);
        matrixFirst.set(0, 2, 1.0);
        matrixFirst.set(0, 3, 7.0);
        matrixFirst.set(0, 4, 1.0);

        matrixFirst.set(1, 0, 5.0);
        matrixFirst.set(1, 1, 3.0);
        matrixFirst.set(1, 2, 2.0);
        matrixFirst.set(1, 3, 0.0);
        matrixFirst.set(1, 4, 0.0);

        matrixSecond.set(0, 0, 7.0);
        matrixSecond.set(0, 1, 5.0);
        matrixSecond.set(0, 2, 1.0);
        matrixSecond.set(0, 3, 2.0);
        matrixSecond.set(0, 4, 4.0);

        matrixSecond.set(1, 0, 1.0);
        matrixSecond.set(1, 1, 1.0);
        matrixSecond.set(1, 2, 1.0);
        matrixSecond.set(1, 3, 1.0);
        matrixSecond.set(1, 4, 1.0);

        operations.operate(matrixFirst, matrixSecond, result, poolProvider.provideThreadPool(), Double::sum);
        poolProvider.waitForCompletion();

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(13.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(0, 3))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(0, 4))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(1, 3))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(1, 4))));
    }

    private void correctEquation_Matrix_v_Matrix_squareMatrix_test() throws InterruptedException, ExecutionException {
        Matrix matrixFirst = new MatJv(3, 3);
        Matrix matrixSecond = new MatJv(3, 3);
        Matrix result = new MatJv(3, 3);

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

        operations.operate(matrixFirst, matrixSecond, result, poolProvider.provideThreadPool(), Double::sum);
        poolProvider.waitForCompletion();

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(13.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    private void correctEquation_Matrix_v_Scalar_longerRow_test() throws ExecutionException, InterruptedException {

        double scalar = 2;
        Matrix matrixFirst = new MatJv(5, 2);
        Matrix result = new MatJv(5, 2);

        matrixFirst.set(0, 0, 5.0);
        matrixFirst.set(0, 1, 8.0);
        matrixFirst.set(1, 0, 1.0);
        matrixFirst.set(1, 1, 7.0);
        matrixFirst.set(2, 0, 1.0);
        matrixFirst.set(2, 1, 5.0);
        matrixFirst.set(3, 0, 3.0);
        matrixFirst.set(3, 1, 2.0);
        matrixFirst.set(4, 0, 0.0);
        matrixFirst.set(4, 1, 0.0);

        operations.operate(matrixFirst, scalar, result, poolProvider.provideThreadPool(), Double::sum);
        poolProvider.waitForCompletion();

        Assertions.assertEquals(7.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(10.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(7.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(3, 0))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(3, 1))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(4, 0))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(4, 1))));
    }

    private void correctEquation_Matrix_v_Scalar_longerColumn_test() throws ExecutionException, InterruptedException {

        double scalar = 3;
        Matrix matrixFirst = new MatJv(2, 5);
        Matrix result = new MatJv(2, 5);

        matrixFirst.set(0, 0, 5.0);
        matrixFirst.set(0, 1, 8.0);
        matrixFirst.set(0, 2, 1.0);
        matrixFirst.set(0, 3, 7.0);
        matrixFirst.set(0, 4, 1.0);
        matrixFirst.set(1, 0, 5.0);
        matrixFirst.set(1, 1, 3.0);
        matrixFirst.set(1, 2, 2.0);
        matrixFirst.set(1, 3, 0.0);
        matrixFirst.set(1, 4, 0.0);

        operations.operate(matrixFirst, scalar, result, poolProvider.provideThreadPool(), Double::sum);
        poolProvider.waitForCompletion();

        Assertions.assertEquals(8.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(11.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(10.0, Double.parseDouble(toOneDecimal.format(result.get(0, 3))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(0, 4))));
        Assertions.assertEquals(8.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 3))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 4))));
    }

    private void correctEquation_Matrix_v_Scalar_squareMatrix_test() throws InterruptedException, ExecutionException {

        double scalar = 4;
        Matrix matrixFirst = new MatJv(3, 3);
        Matrix result = new MatJv(3, 3);

        matrixFirst.set(0, 0, 5.0);
        matrixFirst.set(0, 1, 8.0);
        matrixFirst.set(0, 2, 1.0);
        matrixFirst.set(1, 0, 7.0);
        matrixFirst.set(1, 1, 1.0);
        matrixFirst.set(1, 2, 5.0);
        matrixFirst.set(2, 0, 3.0);
        matrixFirst.set(2, 1, 2.0);
        matrixFirst.set(2, 2, 0.0);

        operations.operate(matrixFirst, scalar, result, poolProvider.provideThreadPool(), Double::sum);
        poolProvider.waitForCompletion();

        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(11.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(7.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }
}