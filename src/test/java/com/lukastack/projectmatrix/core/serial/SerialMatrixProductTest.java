package com.lukastack.projectmatrix.core.serial;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

class SerialMatrixProductTest {

    private SerialMatrixProduct<MatJv> productImpl;
    private final DecimalFormat toThreeDecimal = new DecimalFormat("0.000");

    @BeforeEach
    void setUp() {
        productImpl = new SerialMatrixProduct<>(MatJv.class);
    }

    @Test
    void matMul_correctEquations() {

        Matrix matrixFirst = new MatJv(5, 2);
        Matrix matrixSecond = new MatJv(2, 5);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);
        matrixFirst.set(1, 0, 3.2);
        matrixFirst.set(1, 1, 4.2);
        matrixFirst.set(2, 0, 5.2);
        matrixFirst.set(2, 1, 6.2);
        matrixFirst.set(3, 0, 7.2);
        matrixFirst.set(3, 1, 8.2);
        matrixFirst.set(4, 0, 9.2);
        matrixFirst.set(4, 1, 10.2);

        matrixSecond.set(0, 0, 0.99);
        matrixSecond.set(0, 1, 0.88);
        matrixSecond.set(0, 2, 0.77);
        matrixSecond.set(0, 3, 0.66);
        matrixSecond.set(0, 4, 0.55);
        matrixSecond.set(1, 0, 0.44);
        matrixSecond.set(1, 1, 0.33);
        matrixSecond.set(1, 2, 0.22);
        matrixSecond.set(1, 3, 0.11);
        matrixSecond.set(1, 4, 0.01);

        var result = productImpl.matMul(matrixFirst, matrixSecond);

        System.out.println(matrixFirst);
        System.out.println();
        System.out.println(matrixSecond);
        System.out.println();
        System.out.println(result);

        Assertions.assertEquals(2.156, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(1.782, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.408, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(1.034, Double.parseDouble(toThreeDecimal.format(result.get(0, 3))));
        Assertions.assertEquals(0.682, Double.parseDouble(toThreeDecimal.format(result.get(0, 4))));

        Assertions.assertEquals(5.016, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.202, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(3.388, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(2.574, Double.parseDouble(toThreeDecimal.format(result.get(1, 3))));
        Assertions.assertEquals(1.802, Double.parseDouble(toThreeDecimal.format(result.get(1, 4))));

        Assertions.assertEquals(7.876, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(6.622, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(5.368, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
        Assertions.assertEquals(4.114, Double.parseDouble(toThreeDecimal.format(result.get(2, 3))));
        Assertions.assertEquals(2.922, Double.parseDouble(toThreeDecimal.format(result.get(2, 4))));

        Assertions.assertEquals(10.736, Double.parseDouble(toThreeDecimal.format(result.get(3, 0))));
        Assertions.assertEquals(9.042, Double.parseDouble(toThreeDecimal.format(result.get(3, 1))));
        Assertions.assertEquals(7.348, Double.parseDouble(toThreeDecimal.format(result.get(3, 2))));
        Assertions.assertEquals(5.654, Double.parseDouble(toThreeDecimal.format(result.get(3, 3))));
        Assertions.assertEquals(4.042, Double.parseDouble(toThreeDecimal.format(result.get(3, 4))));

        Assertions.assertEquals(13.596, Double.parseDouble(toThreeDecimal.format(result.get(4, 0))));
        Assertions.assertEquals(11.462, Double.parseDouble(toThreeDecimal.format(result.get(4, 1))));
        Assertions.assertEquals(9.328, Double.parseDouble(toThreeDecimal.format(result.get(4, 2))));
        Assertions.assertEquals(7.194, Double.parseDouble(toThreeDecimal.format(result.get(4, 3))));
        Assertions.assertEquals(5.162, Double.parseDouble(toThreeDecimal.format(result.get(4, 4))));
    }

    @Test
    void createMatrix_createMatJvObject() {
        Matrix matrixFirst = new MatJv(1, 2);
        Matrix matrixSecond = new MatJv(2, 1);

        matrixFirst.set(0, 0, 1.2);
        matrixFirst.set(0, 1, 2.2);

        matrixSecond.set(0, 0, 0.99);
        matrixSecond.set(1, 0, 0.44);

        var result = productImpl.matMul(matrixFirst, matrixSecond);

        Assertions.assertTrue(result instanceof MatJv);
    }
}