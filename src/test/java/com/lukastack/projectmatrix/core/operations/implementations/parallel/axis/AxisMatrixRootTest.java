package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.axis.AxisMatrixRoot;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.column.AxisColumnOperations;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.diagonal.AxisDiagonalOperations;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperations;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class AxisMatrixRootTest {

    private final DecimalFormat toThreeDecimal = new DecimalFormat("0.0");
    private SingletonThreadPoolProvider poolProvider;
    private AxisMatrixRoot rootImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void root_Matrix_x_Matrix_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void root_Matrix_x_Matrix_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void root_Matrix_x_Matrix_correctEquations_DiagonalImplementation() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisDiagonalOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void power_Matrix_x_Scalar_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void power_Matrix_x_Scalar_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void power_Matrix_x_Scalar_correctEquations_DiagonalImplementation() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisDiagonalOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {

        rootImpl = new AxisMatrixRoot(MatJv.class, new AxisColumnOperations());

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = rootImpl.root(matrixFirst, 34, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertTrue(result instanceof MatJv);
    }

    private void test_Matrix_x_Scalar_Equation() throws InterruptedException, ExecutionException {
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

    private void test_Matrix_x_Matrix_Equation() throws InterruptedException, ExecutionException {
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
}