package com.lukastack.projectmatrix.core.operations.implementations.parallel.axis;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.parallel.axis.AxisMatrixRoot;
import com.lukastack.projectmatrix.core.operations.api.parallel.axis.AxisMatrixSubtraction;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.column.AxisColumnOperations;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.diagonal.AxisDiagonalOperations;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperations;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

class AxisMatrixSubtractionTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private SingletonThreadPoolProvider poolProvider;
    private AxisMatrixSubtraction subtractionImpl;

    @BeforeEach
    void setUp() {
        poolProvider = new SingletonThreadPoolProvider();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        poolProvider.close();
    }

    @Test
    void sub_Matrix_x_Matrix_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void sub_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisRowOperations());
        var pool = poolProvider.provideThreadPool();

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> subtractionImpl.sub(matrixFirst, matrixSecond, pool));
    }

    @Test
    void sub_Matrix_x_Matrix_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void sub_Matrix_x_Matrix_ColumnImplementation_WrongDimensions_ThrowsDimensionException() {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisColumnOperations());
        var pool = poolProvider.provideThreadPool();

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> subtractionImpl.sub(matrixFirst, matrixSecond, pool));
    }

    @Test
    void sub_Matrix_x_Matrix_correctEquations_DiagonalImplementation() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisDiagonalOperations());

        test_Matrix_x_Matrix_Equation();
    }

    @Test
    void sub_Matrix_x_Matrix_DiagonalImplementation_WrongDimensions_ThrowsDimensionException() {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisDiagonalOperations());
        var pool = poolProvider.provideThreadPool();

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> subtractionImpl.sub(matrixFirst, matrixSecond, pool));
    }

    @Test
    void sub_Matrix_x_Scalar_correctEquations_RowImplementation() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisRowOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void sub_Matrix_x_Scalar_correctEquations_ColumnImplementation() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisColumnOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void sub_Matrix_x_Scalar_correctEquations_DiagonalImplementation() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisDiagonalOperations());

        test_Matrix_x_Scalar_Equation();
    }

    @Test
    void createMatrix_createMatJvObject() throws ExecutionException, InterruptedException {

        subtractionImpl = new AxisMatrixSubtraction(MatJv.class, new AxisColumnOperations());

        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = subtractionImpl.sub(matrixFirst, 34, poolProvider.provideThreadPool());
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

        var result = subtractionImpl.sub(matrixFirst, matrixSecond, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(-2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(0.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(-3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(-1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
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

        var result = subtractionImpl.sub(matrixFirst, 9, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();

        Assertions.assertEquals(-6.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(-2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(-7.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(-3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(-7.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(-6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(-8.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(-1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(-6.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }
}