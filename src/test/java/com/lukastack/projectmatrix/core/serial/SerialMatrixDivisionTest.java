package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

class SerialMatrixDivisionTest {

    private final DecimalFormat toThreeDecimal = new DecimalFormat("0.000");
    private SerialMatrixDivision<MatJv> divisionImpl;

    @BeforeEach
    void setUp() {
        divisionImpl = new SerialMatrixDivision<>(MatJv.class);
    }

    @Test
    void divide_Matrix_x_Matrix_correctEquations() {

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

        var result = divisionImpl.divide(matrixFirst, matrixSecond);

        Assertions.assertEquals(0.714, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(1.600, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(3.500, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(0.250, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(2.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void divide_Matrix_x_Scalar_correctEquations() {

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

        var result = divisionImpl.divide(matrixFirst, 9);

        Assertions.assertEquals(0.333, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(0.778, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(0.222, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(0.667, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(0.222, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(0.333, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(0.111, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(0.889, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.333, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void createMatrix_createMatJvObject() {
        Matrix matrixFirst = new MatJv(2, 2);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        var result = divisionImpl.divide(matrixFirst, 34);

        Assertions.assertTrue(result instanceof MatJv);
    }
}