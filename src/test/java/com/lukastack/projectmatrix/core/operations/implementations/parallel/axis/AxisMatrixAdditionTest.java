package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.axis.AxisMatrixAddition;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class AxisMatrixAdditionTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private SingletonThreadPoolProvider poolProvider;
    private AxisMatrixAddition additionImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void add_Matrix_x_Matrix_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        additionImpl = new AxisMatrixAddition(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void add_Matrix_x_Matrix_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        additionImpl = new AxisMatrixAddition(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void add_Matrix_x_Scalar_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        additionImpl = new AxisMatrixAddition(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void add_Matrix_x_Scalar_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        additionImpl = new AxisMatrixAddition(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {

        additionImpl = new AxisMatrixAddition(MatJv.class, new AxisRowOperations());

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = additionImpl.add(matrixFirst, 34, poolProvider.provideThreadPool());
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

        var result = additionImpl.add(matrixFirst, matrixSecond, poolProvider.provideThreadPool());

        poolProvider.waitForCompletion();;

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

        var result = additionImpl.add(matrixFirst, 9, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(16.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(11.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(15.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(11.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(10.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(17.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }
}