package com.lukastack.projectmatrix.core.operations.implementations.serial;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.serial.SerialMatrixSubtraction;
import com.lukastack.projectmatrix.errors.DimensionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

class SerialMatrixSubtractionTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private SerialMatrixSubtraction subtractionImpl;

    @BeforeEach
    void setUp() {
        subtractionImpl = new SerialMatrixSubtraction(MatJv.class);
    }

    @Test
    void sub_Matrix_x_Matrix_correctEquations() {

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

        var result = subtractionImpl.sub(matrixFirst, matrixSecond);

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

    @Test
    void sub_WrongDimensions_ThrowsDimensionException() {

        Matrix matrixFirst = new MatJv(2, 2);
        Matrix matrixSecond = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> subtractionImpl.sub(matrixFirst, matrixSecond));
    }

    @Test
    void sub_Matrix_x_Scalar_correctEquations() {

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

        var result = subtractionImpl.sub(matrixFirst, 9);

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

    @Test
    void createMatrix_createMatJvObject() {
        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = subtractionImpl.sub(matrixFirst, 34);

        Assertions.assertTrue(result instanceof MatJv);
    }
}