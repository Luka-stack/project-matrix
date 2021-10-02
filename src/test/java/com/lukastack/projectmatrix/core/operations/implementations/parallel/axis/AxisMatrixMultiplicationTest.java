package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.axis.AxisMatrixMultiplication;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class AxisMatrixMultiplicationTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private ThreadPoolProvider poolProvider;
    private AxisMatrixMultiplication multiplyImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void multiply_Matrix_x_Matrix_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        multiplyImpl = new AxisMatrixMultiplication(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void multiply_Matrix_x_Matrix_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        multiplyImpl = new AxisMatrixMultiplication(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void multiply_Matrix_x_Scalar_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        multiplyImpl = new AxisMatrixMultiplication(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void multiply_Matrix_x_Scalar_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        multiplyImpl = new AxisMatrixMultiplication(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {

        multiplyImpl = new AxisMatrixMultiplication(MatJv.class, new AxisColumnOperations());

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var pool = poolProvider.provideThreadPool();
        var result = multiplyImpl.multiply(matrixFirst, 34, pool);

        poolProvider.waitForCompletion();

        Assertions.assertTrue(result instanceof MatJv);
    }

    private void test_Matrix_x_Matrix_Equation() throws InterruptedException, ExecutionException {
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

    private void test_Matrix_x_Scalar_Equation() throws InterruptedException, ExecutionException {
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
}